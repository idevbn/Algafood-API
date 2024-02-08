package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.in.CozinhaInputDTO;
import com.algaworks.algafood.api.v1.model.out.CozinhaOutputDTO;
import com.algaworks.algafood.core.springdoc.PageableParameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Cozinhas")
@SecurityRequirement(name = "security_auth")
public interface CozinhaControllerOpenApi {

    @PageableParameter
    @Operation(summary = "Lista as cozinhas com paginação")
    ResponseEntity<PagedModel<CozinhaOutputDTO>> listar(
            @Parameter(hidden = true) final Pageable pageable
    );

    @Operation(summary = "Busca uma cozinha por ID", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID da cozinha inválido",
                    content = @Content(schema = @Schema(ref = "ApiError"))),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada",
                    content = @Content(schema = @Schema(ref = "ApiError")))
    })
    ResponseEntity<CozinhaOutputDTO> buscar(final Long cozinhaId);

    @Operation(summary = "Cadastra uma cozinha", responses = {
            @ApiResponse(responseCode = "201", description = "Cozinha cadastrada"),
    })
    ResponseEntity<CozinhaOutputDTO> adicionar(
            @RequestBody(description = "Representação de uma nova cozinha", required = true)
            final CozinhaInputDTO cozinhaInput
    );

    @Operation(summary = "Atualiza uma cozinha por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Cozinha atualizada"),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada",
                    content = @Content(schema = @Schema(ref = "ApiError"))),
    })
    ResponseEntity<CozinhaOutputDTO> atualizar(
            @Parameter(
                    description = "ID de uma cozinha",
                    example = "1",
                    required = true
            )
            final Long cozinhaId,
            @RequestBody(
                    description = "Representação de uma cozinha com os novos dados",
                    required = true
            )
            final CozinhaInputDTO cozinhaInput
    );

    @Operation(summary = "Exclui uma cozinha por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Cozinha excluída"),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada",
                    content = @Content(schema = @Schema(ref = "ApiError")))
    })
    ResponseEntity<Void> remover(
            @Parameter(description = "ID de uma cozinha", example = "1", required = true)
            final Long cozinhaId
    );

}
