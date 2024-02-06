package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.in.CidadeInputDTO;
import com.algaworks.algafood.api.v1.model.out.CidadeOutputDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

/**
 * Interface criada ppara que o Controller que a implemente
 * não necessite das annotations referentes ao Swagger.
 *
 * @author Idevaldo Neto <idevbn@gmail.com>
 */
@Tag(name = "Cidades")
@SecurityRequirement(name = "security_auth")
public interface CidadeControllerOpenApi {

    ResponseEntity<CollectionModel<CidadeOutputDTO>> listar();

    ResponseEntity<CidadeOutputDTO> buscar(final Long id);

    ResponseEntity<CidadeOutputDTO> adicionar(
            final CidadeInputDTO cidadeInputDTO
    );

    ResponseEntity<CidadeOutputDTO> atualizar(final Long id, final CidadeInputDTO cidadeInputDTO);


    ResponseEntity<Void> remover(final Long id);

}
