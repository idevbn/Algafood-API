package com.algaworks.algafood.api.openapi.controllers;

import com.algaworks.algafood.api.exceptionhandler.ApiError;
import com.algaworks.algafood.api.model.in.CozinhaInputDTO;
import com.algaworks.algafood.api.model.out.CozinhaOutputDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

    @ApiOperation("Lista as cozinhas com paginação")
    ResponseEntity<Page<CozinhaOutputDTO>> listar(final Pageable pageable);

    @ApiOperation("Busca uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID da cozinha inválido", content = {
                    @Content(schema = @Schema(implementation = ApiError.class))
            }),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = {
                    @Content(schema = @Schema(implementation = ApiError.class))
            })
    })
    ResponseEntity<CozinhaOutputDTO> buscar(
            @ApiParam(value = "ID de uma cozinha", example = "1", required = true)
            final Long cozinhaId);

    @ApiOperation("Cadastra uma cozinha")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cozinha cadastrada"),
    })
    ResponseEntity<CozinhaOutputDTO> adicionar(
            @ApiParam(name = "corpo", value = "Representação de uma nova cozinha", required = true)
            final CozinhaInputDTO cozinhaInput);

    @ApiOperation("Atualiza uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cozinha atualizada"),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = {
                    @Content(schema = @Schema(implementation = ApiError.class))
            })
    })
    ResponseEntity<CozinhaOutputDTO> atualizar(
            @ApiParam(value = "ID de uma cozinha", example = "1", required = true)
            final Long cozinhaId,

            @ApiParam(name = "corpo", value = "Representação de uma cozinha com os novos dados")
            final CozinhaInputDTO cozinhaInput);

    @ApiOperation("Exclui uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cozinha excluída"),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = {
                    @Content(schema = @Schema(implementation = ApiError.class))
            })
    })
    ResponseEntity<Void> remover(
            @ApiParam(value = "ID de uma cozinha", example = "1", required = true)
            final Long cozinhaId);

}
