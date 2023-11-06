package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.exceptionhandler.ApiError;
import com.algaworks.algafood.api.v1.model.in.EstadoInputDTO;
import com.algaworks.algafood.api.v1.model.out.EstadoOutputDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {

    @ApiOperation("Lista os estados")
    ResponseEntity<CollectionModel<EstadoOutputDTO>> listar();

    @ApiOperation("Busca uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID do estado inválido", content = {
                    @Content(schema = @Schema(implementation = ApiError.class))
            }),
            @ApiResponse(responseCode = "404", description = "Estado não encontrado", content = {
                    @Content(schema = @Schema(implementation = ApiError.class))
            })
    })
    ResponseEntity<EstadoOutputDTO> buscar(
            @ApiParam(value = "ID de um estado", example = "1", required = true)
            final Long estadoId
    );

    @ApiOperation("Cadastra um estado")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Estado cadastrado"),
    })
    ResponseEntity<EstadoOutputDTO> adicionar(
            @ApiParam(name = "corpo", value = "Representação de uma novo estado", required = true)
            final EstadoInputDTO estadoInputDTO
    );

    @ApiOperation("Atualiza um estado por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado atualizado"),
            @ApiResponse(responseCode = "404", description = "Estado não encontrado", content = {
                    @Content(schema = @Schema(implementation = ApiError.class))
            })
    })
    ResponseEntity<EstadoOutputDTO> atualizar(
            @ApiParam(value = "ID de um estado", example = "1", required = true)
            final Long estadoId,

            @ApiParam(name = "corpo", value = "Representação de um estado com os novos dados")
            final EstadoInputDTO estadoInputDTO
    );

    @ApiOperation("Exclui uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Estado excluído"),
            @ApiResponse(responseCode = "404", description = "Estado não encontrado", content = {
                    @Content(schema = @Schema(implementation = ApiError.class))
            })
    })
    ResponseEntity<Void> remover(
            @ApiParam(value = "ID de um estado", example = "1", required = true)
            final Long estadoId
    );

}
