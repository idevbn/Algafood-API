package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.in.CidadeInputDTO;
import com.algaworks.algafood.api.v1.model.out.CidadeOutputDTO;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Lista as cidades")
    ResponseEntity<CollectionModel<CidadeOutputDTO>> listar();

    @Operation(summary = "Busca uma cidade por ID")
    ResponseEntity<CidadeOutputDTO> buscar(final Long id);

    @Operation(
            summary = "Cadastra uma cidade",
            description = "Cadastro de uma cidade necessidade de um estado e um nome válido"
    )
    ResponseEntity<CidadeOutputDTO> adicionar(
            final CidadeInputDTO cidadeInputDTO
    );

    @Operation(summary = "Atualiza uma cidade por ID")
    ResponseEntity<CidadeOutputDTO> atualizar(final Long id, final CidadeInputDTO cidadeInputDTO);

    @Operation(summary = "Exclui uma cidade por ID")
    ResponseEntity<Void> remover(final Long id);

}
