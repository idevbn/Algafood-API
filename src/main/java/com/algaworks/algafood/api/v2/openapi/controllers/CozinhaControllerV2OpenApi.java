package com.algaworks.algafood.api.v2.openapi.controllers;

import com.algaworks.algafood.api.exceptionhandler.ApiError;
import com.algaworks.algafood.api.v2.model.CozinhaOutputDTOV2;
import com.algaworks.algafood.api.v2.model.in.CozinhaInputDTOV2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Cozinhas")
public interface CozinhaControllerV2OpenApi {

    @ApiOperation("Lista as cozinhas com paginação")
    ResponseEntity<PagedModel<CozinhaOutputDTOV2>> listar(final Pageable pageable);

    @ApiOperation("Busca uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID da cozinha inválido",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    })
    })
    ResponseEntity<CozinhaOutputDTOV2> buscar(
            @ApiParam(value = "ID de uma cozinha", example = "1", required = true) final Long cozinhaId
    );

    @ApiOperation("Cadastra uma cozinha")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cozinha cadastrada"),
    })
    ResponseEntity<CozinhaOutputDTOV2> adicionar(
            @ApiParam(name = "corpo", value = "Representação de uma nova cozinha", required = true) final CozinhaInputDTOV2 cozinhaInput
    );

    @ApiOperation("Atualiza uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cozinha atualizada"),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    })
    })
    ResponseEntity<CozinhaOutputDTOV2> atualizar(
            @ApiParam(value = "ID de uma cozinha", example = "1", required = true) final Long cozinhaId,

            @ApiParam(name = "corpo", value = "Representação de uma cozinha com os novos dados",
                    required = true) final CozinhaInputDTOV2 cozinhaInput
    );

    @ApiOperation("Exclui uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cozinha excluída"),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    })
    })
    ResponseEntity<Void> remover(
            @ApiParam(value = "ID de uma cozinha", example = "1", required = true)
            final Long cozinhaId
    );

}
