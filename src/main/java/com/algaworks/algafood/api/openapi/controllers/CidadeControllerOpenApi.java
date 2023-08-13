package com.algaworks.algafood.api.openapi.controllers;

import com.algaworks.algafood.api.exceptionhandler.ApiError;
import com.algaworks.algafood.api.model.in.CidadeInputDTO;
import com.algaworks.algafood.api.model.out.CidadeOutputDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Interface criada ppara que o Controller que a implemente
 * não necessite das annotations referentes ao Swagger.
 *
 * @author Idevaldo Neto <idevbn@gmail.com>
 */
@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

    @ApiOperation("Lista as cidades")
    ResponseEntity<List<CidadeOutputDTO>> listar();

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
    ResponseEntity<CidadeOutputDTO> buscar(@ApiParam(value = "ID de uma cidade", example = "1")
                                           final Long id);

    @ApiOperation("Cadastra uma cidade")
    ResponseEntity<CidadeOutputDTO> adicionar(
            @ApiParam(name = "corpo", value = "Representação de uma nova cidade")
            final CidadeInputDTO cidadeInputDTO
    );

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    })
    })
    ResponseEntity<CidadeOutputDTO> atualizar(
            @ApiParam(value = "ID de uma cidade", example = "1")
            final Long id,
            @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados")
            final CidadeInputDTO cidadeInputDTO
    );

    @ApiOperation("Exclui uma cidade por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    })
    })
    ResponseEntity<Void> remover(
            @ApiParam(value = "ID de uma cidade", example = "1")
            final Long id
    );

}
