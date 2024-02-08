package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.out.GrupoOutputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Usuários")
@SecurityRequirement(name = "security_auth")
public interface UsuarioGrupoControllerOpenApi {

    @Operation(summary = "Lista os grupos associados a um usuário", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = {
                    @Content(schema = @Schema(ref = "ApiError")) }),
    })
    ResponseEntity<CollectionModel<GrupoOutputDTO>> listar(
            @Parameter(description = "ID do usuário", example = "1", required = true)
            final Long usuarioId
    );

    @Operation(summary = "Desassociação de grupo com usuário", responses = {
            @ApiResponse(responseCode = "204", description = "Desassociação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário ou grupo não encontrado", content = {
                    @Content(schema = @Schema(ref = "ApiError")) }),
    })
    ResponseEntity<Void> desassociar(
            @Parameter(description = "ID do usuário", example = "1", required = true)
            final Long usuarioId,
            @Parameter(description = "ID do grupo", example = "1", required = true)
            final Long grupoId
    );

    @Operation(summary = "Associação de grupo com usuário", responses = {
            @ApiResponse(responseCode = "204", description = "Associação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário ou grupo não encontrado", content = {
                    @Content(schema = @Schema(ref = "ApiError")) }),
    })
    ResponseEntity<Void> associar(
            @Parameter(description = "ID do usuário", example = "1", required = true)
            final Long usuarioId,
            @Parameter(description = "ID do grupo", example = "1", required = true)
            final Long grupoId
    );

}
