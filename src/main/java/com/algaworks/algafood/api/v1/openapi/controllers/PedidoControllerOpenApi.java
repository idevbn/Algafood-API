package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.exceptionhandler.ApiError;
import com.algaworks.algafood.api.v1.model.in.PedidoInputDTO;
import com.algaworks.algafood.api.v1.model.out.PedidoOutputDTO;
import com.algaworks.algafood.api.v1.model.out.PedidoResumoOutputDTO;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {

    @ApiImplicitParams({
            @ApiImplicitParam(
                    value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                    name = "campos",
                    paramType = "query",
                    type = "string"
            )
    })
    @ApiOperation("Pesquisa os pedidos")
    ResponseEntity<PagedModel<PedidoResumoOutputDTO>> pesquisar(final Pageable pageable,
                                                                final PedidoFilter filtro);

    @ApiOperation("Registra um pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido registrado"),
    })
    ResponseEntity<PedidoOutputDTO> adicionar(
            @ApiParam(name = "corpo", value = "Representação de um novo pedido", required = true)
            final PedidoInputDTO pedidoInput
    );

    @ApiImplicitParams({
            @ApiImplicitParam(
                    value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                    name = "campos",
                    paramType = "query",
                    type = "string"
            )
    })
    @ApiOperation("Busca um pedido por código")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = {
                    @Content(schema = @Schema(implementation = ApiError.class))
            })
    })
    ResponseEntity<PedidoOutputDTO> buscar(
            @ApiParam(
                    value = "Código de um pedido",
                    example = "f9981ca4-5a5e-4da3-af04-933861df3e55",
                    required = true
            )
            final String codigoPedido
    );

}
