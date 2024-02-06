package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.in.FotoProdutoInputDTO;
import com.algaworks.algafood.api.v1.model.out.FotoProdutoOuputDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface RestauranteFotoProdutoControllerOpenApi {

    ResponseEntity<FotoProdutoOuputDTO> atualizarFoto(final Long restauranteId,
                                                      final Long produtoId,
                                                      final FotoProdutoInputDTO fotoProdutoInput,
                                                      final MultipartFile arquivo) throws IOException;

    ResponseEntity<Void> removerFoto(final Long restauranteId,
                                     final Long produtoId);

    ResponseEntity<?> servirFoto(final Long restauranteId,
                                 final Long produtoId,
                                 final String acceptHeader
    ) throws HttpMediaTypeNotAcceptableException;

}
