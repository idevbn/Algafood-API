package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.exceptionhandler.ApiError;
import com.algaworks.algafood.api.v1.model.in.ProdutoInputDTO;
import com.algaworks.algafood.api.v1.model.out.ProdutoOutputDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Produtos")
public interface RestauranteProdutoControllerOpenApi {

    @ApiOperation("Lista os produtos de um restaurante")
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
                            @Content
                    }
            )
    })
    ResponseEntity<CollectionModel<ProdutoOutputDTO>> listar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            final Long restauranteId,

            @ApiParam(
                    value = "Indica se deve ou não incluir produtos inativos no resultado da listagem",
                    example = "false",
                    defaultValue = "false"
            )
            final Boolean incluirInativos
    );

    @ApiOperation("Busca um produto de um restaurante")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "400",
                    description = "ID do restaurante ou produto inválido",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produto de restaurante não encontrado",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    }
            )
    })
    ResponseEntity<ProdutoOutputDTO> buscar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            final Long restauranteId,

            @ApiParam(value = "ID do produto", example = "1", required = true)
            final Long produtoId
    );

    @ApiOperation("Cadastra um produto de um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto cadastrado"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Restaurante não encontrado",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    }
            )
    })
    ResponseEntity<ProdutoOutputDTO> adicionar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            final Long restauranteId,

            @ApiParam(name = "corpo", value = "Representação de um novo produto", required = true)
            final ProdutoInputDTO produtoInputDTO
    );

    @ApiOperation("Atualiza um produto de um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produto de restaurante não encontrado",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    }
            )
    })
    ResponseEntity<ProdutoOutputDTO> atualizar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            final Long restauranteId,

            @ApiParam(value = "ID do produto", example = "1", required = true)
            final Long produtoId,

            @ApiParam(name = "corpo", value = "Representação de um produto com os novos dados",
                    required = true)
            final ProdutoInputDTO produtoInputDTO
    );

}
