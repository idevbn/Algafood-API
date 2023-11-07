package com.algaworks.algafood.api.v2.openapi.controllers;

import com.algaworks.algafood.api.exceptionhandler.ApiError;
import com.algaworks.algafood.api.v2.model.in.CidadeInputDTOV2;
import com.algaworks.algafood.api.v2.model.CidadeOutputDTOV2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

/**
 * Interface criada ppara que o Controller que a implemente
 * não necessite das annotations referentes ao Swagger.
 *
 * @author Idevaldo Neto <idevbn@gmail.com>
 */
@Api(tags = "Cidades")
public interface CidadeControllerV2OpenApi {

    @ApiOperation("Lista as cidades")
    ResponseEntity<CollectionModel<CidadeOutputDTOV2>> listar();

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID da cidade inválido",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    })
    })
    ResponseEntity<CidadeOutputDTOV2> buscar(
            @ApiParam(value = "ID de uma cidade", example = "1", required = true) final Long id
    );

    @ApiOperation("Cadastra uma cidade")
    ResponseEntity<CidadeOutputDTOV2> adicionar(
            @ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true)
            final CidadeInputDTOV2 cidadeInputDTO
    );

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    })
    })
    ResponseEntity<CidadeOutputDTOV2> atualizar(
            @ApiParam(value = "ID de uma cidade", example = "1", required = true)
            final Long id,
            @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados")
            final CidadeInputDTOV2 cidadeInputDTO
    );

    @ApiOperation("Exclui uma cidade por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    })
    })
    ResponseEntity<Void> remover(
            @ApiParam(value = "ID de uma cidade", example = "1", required = true)
            final Long id
    );

}

