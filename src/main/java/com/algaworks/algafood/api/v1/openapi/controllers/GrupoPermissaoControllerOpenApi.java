package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.out.PermissaoOutputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Grupos")
@SecurityRequirement(name = "security_auth")
public interface GrupoPermissaoControllerOpenApi {

    @Operation(summary = "Lista as permissões associadas a um grupo", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID do grupo inválido", content = {
                    @Content(schema = @Schema(ref = "ApiError")) }),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = {
                    @Content(schema = @Schema(ref = "ApiError")) }),
    })
    ResponseEntity<CollectionModel<PermissaoOutputDTO>> listar(
            @Parameter(description = "ID de um grupo", example = "1", required = true)
            final Long grupoId
    );

    @Operation(summary = "Desassociação de permissão com grupo", responses = {
            @ApiResponse(responseCode = "204", description = "Desassociação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Grupo ou permissão não encontrada", content = {
                    @Content(schema = @Schema(ref = "ApiError")) }),
    })
    ResponseEntity<Void> desassociar(
            @Parameter(description = "ID de um grupo", example = "1", required = true)
            final Long grupoId,
            @Parameter(description = "ID de uma permissão", example = "1", required = true)
            final Long permissaoId
    );

    @Operation(summary = "Associação de permissão com grupo", responses = {
            @ApiResponse(responseCode = "204", description = "Associação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Grupo ou permissão não encontrada", content = {
                    @Content(schema = @Schema(ref = "ApiError")) }),
    })
    ResponseEntity<Void> associar(
            @Parameter(description = "ID de um grupo", example = "1", required = true)
            final Long grupoId,
            @Parameter(description = "ID de uma permissão", example = "1", required = true)
            final Long permissaoId
    );

}
