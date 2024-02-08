package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.out.FormaPagamentoOutputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Restaurantes")
@SecurityRequirement(name = "security_auth")
public interface RestauranteFormaPagamentoControllerOpenApi {

    @Operation(summary = "Lista as formas de pagamento associadas a restaurante", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "ApiError")) }),
    })
    ResponseEntity<CollectionModel<FormaPagamentoOutputDTO>> listar(
            @Parameter(description = "ID do restaurante", example = "1", required = true)
            final Long restauranteId
    );

    @Operation(summary = "Desassociação de restaurante com forma de pagamento", responses = {
            @ApiResponse(responseCode = "204", description = "Desassociação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante ou forma de pagamento não encontrado", content = {
                    @Content(schema = @Schema(ref = "ApiError")) }),
    })
    ResponseEntity<Void> desassociar(
            @Parameter(description = "ID do restaurante", example = "1", required = true)
            final Long restauranteId,
            @Parameter(description = "ID da forma de pagamento", example = "1", required = true)
            final Long formaPagamentoId
    );

    @Operation(summary = "Associação de restaurante com forma de pagamento", responses = {
            @ApiResponse(responseCode = "204", description = "Associação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante ou forma de pagamento não encontrado", content = {
                    @Content(schema = @Schema(ref = "ApiError")) }),
    })
    ResponseEntity<Void> associar(
            @Parameter(description = "ID do restaurante", example = "1", required = true)
            final Long restauranteId,
            @Parameter(description = "ID da forma de pagamento", example = "1", required = true)
            final Long formaPagamentoId
    );

}
