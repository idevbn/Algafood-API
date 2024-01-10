package com.algaworks.algafood.api.v1.controllers;

import com.algaworks.algafood.api.v1.assembler.FotoProdutoOutputDTOAssembler;
import com.algaworks.algafood.api.v1.model.in.FotoProdutoInputDTO;
import com.algaworks.algafood.api.v1.model.out.FotoProdutoOuputDTO;
import com.algaworks.algafood.api.v1.openapi.controllers.RestauranteFotoProdutoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/produtos/{produtoId}/foto",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFotoProdutoController implements RestauranteFotoProdutoControllerOpenApi {

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

    @GetMapping
    @CheckSecurity.Restaurantes.PodeConsultar
    public ResponseEntity<?> recuperarFoto(
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

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FotoProdutoOuputDTO> atualizarFoto(
            @PathVariable("restauranteId") final Long restauranteId,
            @PathVariable("produtoId") final Long produtoId,
            @Valid final FotoProdutoInputDTO fotoProdutoInput,
            @RequestPart(required = true) final MultipartFile arquivo
    ) throws IOException {

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

    @GetMapping(produces = {
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE,
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<?> servirFoto(
            @PathVariable("restauranteId") final Long restauranteId,
            @PathVariable("produtoId") final Long produtoId,
            @RequestHeader(name = "accept") final String acceptHeader
    ) throws HttpMediaTypeNotAcceptableException {

        if (acceptHeader.equals(MediaType.APPLICATION_JSON_VALUE)) {
            return this.recuperarFoto(restauranteId, produtoId);
        }

        try {
            final FotoProduto fotoProduto = this.fotoProdutoService
                    .buscarOuFalhar(restauranteId, produtoId);

            final MediaType mediaTypeFoto = MediaType
                    .parseMediaType(fotoProduto.getContentType());

            final List<MediaType> mediaTypesAceitas = MediaType
                    .parseMediaTypes(acceptHeader);

            this.verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);

            final String nomeArquivo = fotoProduto.getNomeArquivo();

            final FotoStorageService.FotoRecuperada fotoRecuperada = this.storageService
                    .recuperar(nomeArquivo);

            if (fotoRecuperada.temUrl()) {
                final ResponseEntity<Object> response = ResponseEntity
                        .status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
                        .build();

                return response;
            } else {

                final InputStream inputStream = fotoRecuperada.getInputStream();

                final InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

                final ResponseEntity<InputStreamResource> response = ResponseEntity
                        .status(HttpStatus.OK)
                        .contentType(mediaTypeFoto)
                        .body(inputStreamResource);

                return response;
            }
        } catch (final EntidadeNaoEncontradaException e) {
            final ResponseEntity<InputStreamResource> response = ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();

            return response;
        }
    }

    @DeleteMapping
    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    public ResponseEntity<Void> removerFoto(
            @PathVariable("restauranteId") final Long restauranteId,
            @PathVariable("produtoId") final Long produtoId
    ) {
        this.fotoProdutoService.excluir(restauranteId, produtoId);

        final ResponseEntity<Void> response = ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

        return response;
    }

    private void verificarCompatibilidadeMediaType(final MediaType mediaTypeFoto,
                                                   final List<MediaType> mediaTypesAceitas)
            throws HttpMediaTypeNotAcceptableException {

        final boolean compativel = mediaTypesAceitas
                .stream()
                .anyMatch(mediaTypesAceita ->
                        mediaTypesAceita.isCompatibleWith(mediaTypeFoto)
                );

        if (!compativel) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
        }
    }

}
