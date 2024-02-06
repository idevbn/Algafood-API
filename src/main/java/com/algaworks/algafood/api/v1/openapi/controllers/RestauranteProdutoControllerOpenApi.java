package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.in.ProdutoInputDTO;
import com.algaworks.algafood.api.v1.model.out.ProdutoOutputDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

public interface RestauranteProdutoControllerOpenApi {

    ResponseEntity<CollectionModel<ProdutoOutputDTO>> listar(final Long restauranteId,
                                                             final Boolean incluirInativos);

    ResponseEntity<ProdutoOutputDTO> buscar(final Long restauranteId,
                                            final Long produtoId);

    ResponseEntity<ProdutoOutputDTO> adicionar(final Long restauranteId,
                                               final ProdutoInputDTO produtoInputDTO);

    ResponseEntity<ProdutoOutputDTO> atualizar(final Long restauranteId,
                                               final Long produtoId,
                                               final ProdutoInputDTO produtoInputDTO);

}
