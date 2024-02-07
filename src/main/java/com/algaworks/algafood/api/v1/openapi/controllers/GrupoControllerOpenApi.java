package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.in.GrupoInputDTO;
import com.algaworks.algafood.api.v1.model.out.GrupoOutputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Grupos")
@SecurityRequirement(name = "security_auth")
public interface GrupoControllerOpenApi {

    @Operation(summary = "Lista os grupos")
    ResponseEntity<CollectionModel<GrupoOutputDTO>> listar();

    @Operation(summary = "Busca um grupo por ID")
    ResponseEntity<GrupoOutputDTO> buscar(
            @Parameter(description = "ID de um grupo", example = "1", required = true)
            final Long id
    );

    @Operation(summary = "Cadastra um grupo")
    ResponseEntity<GrupoOutputDTO> adicionar(
            @RequestBody(description = "Representação de um novo grupo", required = true)
            final GrupoInputDTO grupoInputDTO
    );

    @Operation(summary = "Atualiza um grupo por ID")
    ResponseEntity<GrupoOutputDTO> atualizar(
            @Parameter(description = "ID de um grupo", example = "1", required = true)
            final Long id,
            @RequestBody(description = "Representação de um grupo com os novos dados", required = true)
            final GrupoInputDTO grupoInputDTO
    );

    @Operation(summary = "Exclui um grupo por ID")
    ResponseEntity<Void> excluir(final Long id);

}
