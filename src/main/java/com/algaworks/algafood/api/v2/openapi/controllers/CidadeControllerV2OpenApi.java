package com.algaworks.algafood.api.v2.openapi.controllers;

import com.algaworks.algafood.api.v2.model.CidadeOutputDTOV2;
import com.algaworks.algafood.api.v2.model.in.CidadeInputDTOV2;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

/**
 * Interface criada ppara que o Controller que a implemente
 * não necessite das annotations referentes ao Swagger.
 *
 * @author Idevaldo Neto <idevbn@gmail.com>
 */
public interface CidadeControllerV2OpenApi {

    ResponseEntity<CollectionModel<CidadeOutputDTOV2>> listar();

    ResponseEntity<CidadeOutputDTOV2> buscar(final Long id);

    ResponseEntity<CidadeOutputDTOV2> adicionar(final CidadeInputDTOV2 cidadeInputDTO);

    ResponseEntity<CidadeOutputDTOV2> atualizar(final Long id,
                                                final CidadeInputDTOV2 cidadeInputDTO);

    ResponseEntity<Void> remover(final Long id);

}

