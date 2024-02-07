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
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    ResponseEntity<Void> removerFoto(final Long restauranteId,
                                     final Long produtoId);

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
            final Long restauranteId,
            final Long produtoId
    );

    @Operation(hidden = true)
    ResponseEntity<?> servirFoto(final Long restauranteId,
                                 final Long produtoId,
                                 final String acceptHeader
    ) throws HttpMediaTypeNotAcceptableException;

}
