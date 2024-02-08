package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.in.FormaPagamentoInputDTO;
import com.algaworks.algafood.api.v1.model.out.FormaPagamentoOutputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

@Tag(name = "Formas de pagamento")
@SecurityRequirement(name = "security_auth")
public interface FormasPagamentoControllerOpenApi {

    @Operation(summary = "Lista as formas de pagamento")
    ResponseEntity<CollectionModel<FormaPagamentoOutputDTO>> listar(
            @Parameter(hidden = true) final ServletWebRequest servletWebRequest
    );

    @Operation(summary = "Busca uma forma de pagamento por ID", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID da forma de pagamento inválido", content = {
                    @Content(schema = @Schema(ref = "ApiError"))}),
            @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = {
                    @Content(schema = @Schema(ref = "ApiError"))})
    })
    ResponseEntity<FormaPagamentoOutputDTO> buscar(
            @Parameter(description = "ID de uma forma de pagamento", example = "1", required = true)
            final Long formaPagamentoId,
            @Parameter(hidden = true)
            final ServletWebRequest request
    );

    @Operation(summary = "Cadastra uma forma de pagamento", responses = {
            @ApiResponse(responseCode = "201", description = "Forma de pagamento cadastrada")})
    ResponseEntity<FormaPagamentoOutputDTO> adicionar(
            @RequestBody(
                    description = "Representação de uma nova forma de pagamento",
                    required = true
            )
            final FormaPagamentoInputDTO formaPagamentoInputDTO
    );

    @Operation(summary = "Atualiza uma forma de pagamento por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Forma de pagamento atualizada"),
            @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = {
                    @Content(schema = @Schema(ref = "ApiError")) })
    })
    ResponseEntity<FormaPagamentoOutputDTO> atualizar(
            @Parameter(description = "ID de uma forma de pagamento", example = "1", required = true)
            final Long formaPagamentoId,
            @RequestBody(
                    description = "Representação de uma forma de pagamento com os novos dados",
                    required = true
            )
            final FormaPagamentoInputDTO formaPagamentoInputDTO
    );

    @Operation(summary = "Exclui uma forma de pagamento por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Forma de pagamento excluída"),
            @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = {
                    @Content(schema = @Schema(ref = "ApiError")) })
    })
    ResponseEntity<Void> remover(
            @Parameter(description = "ID de uma forma de pagamento", example = "1", required = true)
            final Long formaPagamentoId
    );

}
