package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.api.assembler.FotoProdutoOutputDTOAssembler;
import com.algaworks.algafood.api.model.in.FotoProdutoInputDTO;
import com.algaworks.algafood.api.model.out.FotoProdutoOuputDTO;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    private final CatalogoFotoProdutoService fotoProdutoService;

    private final CadastroProdutoService produtoService;

    private final FotoProdutoOutputDTOAssembler assembler;

    @Autowired
    public RestauranteProdutoFotoController(final CatalogoFotoProdutoService fotoProdutoService,
                                            final CadastroProdutoService produtoService,
                                            final FotoProdutoOutputDTOAssembler assembler) {
        this.fotoProdutoService = fotoProdutoService;
        this.produtoService = produtoService;
        this.assembler = assembler;
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FotoProdutoOuputDTO> atualizarFoto(
            @PathVariable("restauranteId") final Long restauranteId,
            @PathVariable("produtoId") final Long produtoId,
            @Valid final FotoProdutoInputDTO fotoProdutoInput
            ) {

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
                .salvar(foto, foto.getI);

        final FotoProdutoOuputDTO fotoProdutoOuputDTO = this.assembler
                .toModel(fotoSalva);

        final ResponseEntity<FotoProdutoOuputDTO> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(fotoProdutoOuputDTO);

        return response;
    }

}
