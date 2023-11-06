package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.exceptionhandler.ApiError;
import com.algaworks.algafood.api.v1.model.in.RestauranteInputDTO;
import com.algaworks.algafood.api.v1.model.out.RestauranteApenasNomeOutputDTO;
import com.algaworks.algafood.api.v1.model.out.RestauranteBasicoOutputDTO;
import com.algaworks.algafood.api.v1.model.out.RestauranteOutputDTO;
import com.algaworks.algafood.api.v1.openapi.model.RestauranteBasicoModelOpenApi;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

    @ApiImplicitParams({
            @ApiImplicitParam(
                    value = "Nome da projeção de pedidos",
                    allowableValues = "apenas-nome",
                    name = "projecao",
                    paramType = "query",
                    dataType = "java.lang.String"
            )
    })
    @ApiOperation(value = "Lista restaurantes", response = RestauranteBasicoModelOpenApi.class)
    ResponseEntity<CollectionModel<RestauranteBasicoOutputDTO>> listar();

//    @JsonView(RestauranteView.Resumo.class)
//    @ApiOperation(value = "Lista restaurantes", hidden = true)
//    public ResponseEntity<CollectionModel<RestauranteBasicoOutputDTO>> listarResumido();

    @ApiIgnore
    @ApiOperation(value = "Lista restaurantes", hidden = true)
    ResponseEntity<CollectionModel<RestauranteApenasNomeOutputDTO>> listarApenasNomes();

    @ApiOperation("Busca um restaurante por ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "400",
                    description = "ID do restaurante inválido",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Restaurante não encontrado",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    }
            )
    })
    ResponseEntity<RestauranteOutputDTO> buscar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true) final Long restauranteId
    );

    @ApiOperation("Cadastra um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Restaurante cadastrado"),
    })
    ResponseEntity<RestauranteOutputDTO> adicionar(
            @ApiParam(name = "corpo", value = "Representação de um novo restaurante", required = true) final RestauranteInputDTO restauranteInputDTO
    );

    @ApiOperation("Atualiza um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Restaurante não encontrado",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    }
            )
    })
    ResponseEntity<RestauranteOutputDTO> atualizar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId,

            @ApiParam(
                    name = "corpo",
                    value = "Representação de um restaurante com os novos dados",
                    required = true
            ) final RestauranteInputDTO restauranteInputDTO
    );

    @ApiOperation("Ativa um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurante ativado com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Restaurante não encontrado",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    }
            )
    })
    ResponseEntity<Void> ativar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true) final Long restauranteId
    );

    @ApiOperation("Inativa um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurante inativado com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Restaurante não encontrado",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    }
            )
    })
    ResponseEntity<Void> inativar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true) final Long restauranteId
    );

    @ApiOperation("Ativa múltiplos restaurantes")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso")
    })
    ResponseEntity<Void> ativarMultiplos(
            @ApiParam(name = "corpo", value = "IDs de restaurantes", required = true) final List<Long> restauranteIds
    );

    @ApiOperation("Inativa múltiplos restaurantes")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso")
    })
    ResponseEntity<Void> inativarMultiplos(
            @ApiParam(name = "corpo", value = "IDs de restaurantes", required = true) final List<Long> restauranteIds
    );

    @ApiOperation("Abre um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurante aberto com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Restaurante não encontrado",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    }
            )
    })
    ResponseEntity<Void> abrir(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true) final Long restauranteId
    );

    @ApiOperation("Fecha um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurante fechado com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Restaurante não encontrado",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    })
    })
    ResponseEntity<Void> fechar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true) final Long restauranteId
    );

}
