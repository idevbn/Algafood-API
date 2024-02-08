package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.in.EstadoInputDTO;
import com.algaworks.algafood.api.v1.model.out.EstadoOutputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Estados")
@SecurityRequirement(name = "security_auth")
public interface EstadoControllerOpenApi {

    @Operation(summary = "Lista os estados")
    ResponseEntity<CollectionModel<EstadoOutputDTO>> listar();

    @Operation(summary = "Busca um estado por ID", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID do estado inválido", content = {
                    @Content(schema = @Schema(ref = "ApiError")) }),
            @ApiResponse(responseCode = "404", description = "Estado não encontrado", content = {
                    @Content(schema = @Schema(ref = "ApiError")) })
    })
    ResponseEntity<EstadoOutputDTO> buscar(
            @Parameter(description = "ID de um estado", example = "1", required = true)
            final Long estadoId
    );

    @Operation(summary = "Cadastra um estado", responses = {
            @ApiResponse(responseCode = "201", description = "Estado cadastrado")
    })
    ResponseEntity<EstadoOutputDTO> adicionar(
            @RequestBody(description = "Representação de um novo estado", required = true)
            final EstadoInputDTO estadoInputDTO
    );

    @Operation(summary = "Atualiza um estado por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Estado atualizado"),
            @ApiResponse(responseCode = "404", description = "Estado não encontrado", content = {
                    @Content(schema = @Schema(ref = "ApiError")) })
    })
    ResponseEntity<EstadoOutputDTO> atualizar(
            @Parameter(description = "ID de um estado", example = "1", required = true)
            final Long estadoId,
            @RequestBody(description = "Representação de um estado com os novos dados", required = true)
            final EstadoInputDTO estadoInputDTO
    );
    @Operation(summary = "Exclui um estado por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Estado excluído"),
            @ApiResponse(responseCode = "404", description = "Estado não encontrado", content = {
                    @Content(schema = @Schema(ref = "ApiError")) })
    })
    ResponseEntity<Void> remover(
            @Parameter(description = "ID de um estado", example = "1", required = true)
            final Long estadoId
    );

}
