package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.in.FotoProdutoInputDTO;
import com.algaworks.algafood.api.v1.model.out.FotoProdutoOuputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import java.io.IOException;

@Tag(name = "Produtos")
@SecurityRequirement(name = "security_auth")
public interface RestauranteFotoProdutoControllerOpenApi {

    @Operation(summary = "Atualiza a foto do produto de um restaurante")
    ResponseEntity<FotoProdutoOuputDTO> atualizarFoto(
            @Parameter(description = "Id do restaurante", example = "1", required = true)
            final Long restauranteId,
            @Parameter(description = "Id do produto", example = "2", required = true)
            final Long produtoId,
            @RequestBody(required = true)
            final FotoProdutoInputDTO fotoProdutoInput
    ) throws IOException;

    @Operation(summary = "Exclui a foto do produto de um restaurante", responses = {
            @ApiResponse(responseCode = "204", description = "Foto do produto excluída"),
            @ApiResponse(responseCode = "400", description = "ID do restaurante ou produto inválido", content = {
                    @Content(schema = @Schema(ref = "ApiError")) }),
            @ApiResponse(responseCode = "404", description = "Foto de produto não encontrada", content = {
                    @Content(schema = @Schema(ref = "ApiError")) }),
    })
    ResponseEntity<Void> removerFoto(
            @Parameter(description = "ID do restaurante", example = "1", required = true)
            final Long restauranteId,
            @Parameter(description = "ID do produto", example = "1", required = true)
            final Long produtoId
    );

    @Operation(summary = "Busca a foto do produto de um restaurante", responses = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FotoProdutoOuputDTO.class)
                    ),
                    @Content(
                            mediaType = "image/jpeg",
                            schema = @Schema(type = "string", format = "binary")
                    ),
                    @Content(
                            mediaType = "image/png",
                            schema = @Schema(type = "string", format = "binary")
                    )
            })
    })
    ResponseEntity<FotoProdutoOuputDTO> recuperarFoto(
            @Parameter(description = "ID do restaurante", example = "1", required = true)
            final Long restauranteId,
            @Parameter(description = "ID do produto", example = "1", required = true)
            final Long produtoId
    );

    @Operation(hidden = true)
    ResponseEntity<?> servirFoto(final Long restauranteId,
                                 final Long produtoId,
                                 final String acceptHeader
    ) throws HttpMediaTypeNotAcceptableException;

}
