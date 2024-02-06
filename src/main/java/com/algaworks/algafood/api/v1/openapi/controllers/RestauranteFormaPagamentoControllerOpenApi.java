package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.out.FormaPagamentoOutputDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

public interface RestauranteFormaPagamentoControllerOpenApi {

    ResponseEntity<CollectionModel<FormaPagamentoOutputDTO>> listar(final Long restauranteId);

    ResponseEntity<Void> desassociar(final Long restauranteId,
                                     final Long formaPagamentoId);

    ResponseEntity<Void> associar(final Long restauranteId,
                                  final Long formaPagamentoId);

}
