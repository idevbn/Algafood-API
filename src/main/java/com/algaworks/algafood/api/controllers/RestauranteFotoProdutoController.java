package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.api.assembler.FotoProdutoOutputDTOAssembler;
import com.algaworks.algafood.api.model.in.FotoProdutoInputDTO;
import com.algaworks.algafood.api.model.out.FotoProdutoOuputDTO;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteFotoProdutoController {

    private final CatalogoFotoProdutoService fotoProdutoService;

    private final CadastroProdutoService produtoService;

    private final FotoStorageService storageService;

    private final FotoProdutoOutputDTOAssembler assembler;

    @Autowired
    public RestauranteFotoProdutoController(final CatalogoFotoProdutoService fotoProdutoService,
                                            final CadastroProdutoService produtoService,
                                            final FotoStorageService storageService,
                                            final FotoProdutoOutputDTOAssembler assembler) {
        this.fotoProdutoService = fotoProdutoService;
        this.produtoService = produtoService;
        this.storageService = storageService;
        this.assembler = assembler;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FotoProdutoOuputDTO> recuperarFoto(
            @PathVariable("restauranteId") final Long restauranteId,
            @PathVariable("produtoId") final Long produtoId
    ) {
        final FotoProduto foto = this.fotoProdutoService
                .buscarOuFalhar(restauranteId, produtoId);

        final FotoProdutoOuputDTO fotoProdutoOuputDTO = this.assembler
                .toModel(foto);

        final ResponseEntity<FotoProdutoOuputDTO> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(fotoProdutoOuputDTO);

        return response;
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FotoProdutoOuputDTO> atualizarFoto(
            @PathVariable("restauranteId") final Long restauranteId,
            @PathVariable("produtoId") final Long produtoId,
            @Valid final FotoProdutoInputDTO fotoProdutoInput
            ) throws IOException {

        final MultipartFile arquivo = fotoProdutoInput.getArquivo();

        final Produto produto = this.produtoService
                .buscarOuFalhar(restauranteId, produtoId);

        final FotoProduto foto = new FotoProduto();
        foto.setProduto(produto);
        foto.setDescricao(fotoProdutoInput.getDescricao());
        foto.setContentType(arquivo.getContentType());
        foto.setTamanho(arquivo.getSize());
        foto.setNomeArquivo(arquivo.getOriginalFilename());

        final FotoProduto fotoSalva = this.fotoProdutoService
                .salvar(foto, arquivo.getInputStream());

        final FotoProdutoOuputDTO fotoProdutoOuputDTO = this.assembler
                .toModel(fotoSalva);

        final ResponseEntity<FotoProdutoOuputDTO> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(fotoProdutoOuputDTO);

        return response;
    }

    @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> servirFoto(
            @PathVariable("restauranteId") final Long restauranteId,
            @PathVariable("produtoId") final Long produtoId
    ) {
        try {
            final FotoProduto fotoProduto = this.fotoProdutoService
                    .buscarOuFalhar(restauranteId, produtoId);

            final String nomeArquivo = fotoProduto.getNomeArquivo();

            final InputStream inputStream = this.storageService
                    .recuperar(nomeArquivo);

            final InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

            final ResponseEntity<InputStreamResource> response = ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(inputStreamResource);

            return response;
        } catch (final EntidadeNaoEncontradaException e) {
            final ResponseEntity<InputStreamResource> response = ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();

            return response;
        }
    }

}
