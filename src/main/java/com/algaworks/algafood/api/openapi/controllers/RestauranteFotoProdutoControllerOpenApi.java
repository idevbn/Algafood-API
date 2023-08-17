package com.algaworks.algafood.api.openapi.controllers;

import com.algaworks.algafood.api.exceptionhandler.ApiError;
import com.algaworks.algafood.api.model.in.FotoProdutoInputDTO;
import com.algaworks.algafood.api.model.out.FotoProdutoOuputDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import java.io.IOException;

@Api(tags = "Produtos")
public interface RestauranteFotoProdutoControllerOpenApi {

    @ApiOperation("Atualiza a foto do produto de um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Foto do produto atualizada"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produto de restaurante não encontrado",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    }
            )
    })
    ResponseEntity<FotoProdutoOuputDTO> atualizarFoto(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            final Long restauranteId,

            @ApiParam(value = "ID do produto", example = "1", required = true)
            final Long produtoId,

            final FotoProdutoInputDTO fotoProdutoInput
    ) throws IOException;

    @ApiOperation("Exclui a foto do produto de um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Foto do produto excluída"),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID do restaurante ou produto inválido",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Foto de produto não encontrada",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    }
            )
    })
    ResponseEntity<Void> removerFoto(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            final Long restauranteId,

            @ApiParam(value = "ID do produto", example = "1", required = true)
            final Long produtoId
    );

    @ApiOperation(value = "Busca a foto do produto de um restaurante",
            produces = "application/json, image/jpeg, image/png")
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
                    description = "Foto de produto não encontrada",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    }
            )
    })
    ResponseEntity<FotoProdutoOuputDTO> recuperarFoto(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            final Long restauranteId,

            @ApiParam(value = "ID do produto", example = "1", required = true)
            final Long produtoId
    );

    @ApiOperation(value = "Busca a foto do produto de um restaurante", hidden = true)
    ResponseEntity<?> servirFoto(
            final Long restauranteId,
            final Long produtoId,
            final String acceptHeader
    ) throws HttpMediaTypeNotAcceptableException;

}
