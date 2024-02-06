package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.in.CidadeInputDTO;
import com.algaworks.algafood.api.v1.model.out.CidadeOutputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
    ResponseEntity<CidadeOutputDTO> buscar(
            @Parameter(
                    description = "ID de uma cidade",
                    example = "1",
                    required = true
            )
            final Long id
    );

    @Operation(
            summary = "Cadastra uma cidade",
            description = "Cadastro de uma cidade necessidade de um estado e um nome válido"
    )
    ResponseEntity<CidadeOutputDTO> adicionar(
            @RequestBody(
                    description = "Representação de uma nova cidade",
                    required = true
            )
            final CidadeInputDTO cidadeInputDTO
    );

    @Operation(summary = "Atualiza uma cidade por ID")
    ResponseEntity<CidadeOutputDTO> atualizar(
            @Parameter(
                    description = "ID de uma cidade",
                    example = "1",
                    required = true
            ) final Long id,
            @RequestBody(
                    description = "Representação de uma cidade com dados atualizados",
                    required = true
            )
            final CidadeInputDTO cidadeInputDTO
    );

    @Operation(summary = "Exclui uma cidade por ID")
    ResponseEntity<Void> remover(
            @Parameter(
                    description = "ID de uma cidade",
                    example = "1",
                    required = true
            )
            final Long id
    );

}
