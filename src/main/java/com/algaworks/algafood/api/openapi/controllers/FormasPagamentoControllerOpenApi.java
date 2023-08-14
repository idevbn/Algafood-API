package com.algaworks.algafood.api.openapi.controllers;

import com.algaworks.algafood.api.exceptionhandler.ApiError;
import com.algaworks.algafood.api.model.in.FormaPagamentoInputDTO;
import com.algaworks.algafood.api.model.out.FormaPagamentoOutputDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;

@Api(tags = "Formas de pagamento")
public interface FormasPagamentoControllerOpenApi {

    @ApiOperation("Lista as formas de pagamento")
    ResponseEntity<List<FormaPagamentoOutputDTO>> listar(final ServletWebRequest request);

    @ApiOperation("Busca uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID da forma de pagamento inválido",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    })
    })
    ResponseEntity<FormaPagamentoOutputDTO> buscar(
            @ApiParam(value = "ID de uma forma de pagamento", example = "1")
            final Long formaPagamentoId,
            final ServletWebRequest request);

    @ApiOperation("Cadastra uma forma de pagamento")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Forma de pagamento cadastrada"),
    })
    ResponseEntity<FormaPagamentoOutputDTO> adicionar(
            @ApiParam(name = "corpo", value = "Representação de uma nova forma de pagamento")
            final FormaPagamentoInputDTO formaPagamentoInputDTO);

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Forma de pagamento atualizada"),
            @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada",
                    content = {
                    @Content(schema = @Schema(implementation = ApiError.class))
                    })
    })
    ResponseEntity<FormaPagamentoOutputDTO> atualizar(
            @ApiParam(value = "ID de uma forma de pagamento", example = "1")
            final Long formaPagamentoId,
            @ApiParam(name = "corpo", value = "Representação de uma forma de pagamento com os novos dados")
            final FormaPagamentoInputDTO formaPagamentoInputDTO);

    @ApiOperation("Exclui uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Forma de pagamento excluída"),
            @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada",
                    content = {
                    @Content(schema = @Schema(implementation = ApiError.class))
                    })
    })
    ResponseEntity<Void> remover(final Long formaPagamentoId);

}
