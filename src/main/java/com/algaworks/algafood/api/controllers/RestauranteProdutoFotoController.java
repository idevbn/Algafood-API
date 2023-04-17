package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.api.model.in.FotoProdutoInputDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> atualizarFoto(
            @PathVariable("restauranteId") final Long restauranteId,
            @PathVariable("produtoId") final Long produtoId,
            final FotoProdutoInputDTO fotoProdutoInput
            ) {

        final String nomeArquivo = UUID.randomUUID() + "_"
                + fotoProdutoInput.getArquivo().getOriginalFilename();

        final Path arquivoFoto = Path
                .of("/home/idevaldo/Documentos/Java/Algaworks/projeto/catalogo",
                nomeArquivo);

        System.out.println(fotoProdutoInput.getDescricao());
        System.out.println(arquivoFoto);
        System.out.println(fotoProdutoInput.getArquivo().getContentType());

        try {
            fotoProdutoInput.getArquivo().transferTo(arquivoFoto);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }

        final ResponseEntity<Void> response = ResponseEntity
                .status(HttpStatus.OK)
                .build();

        return response;
    }

}
