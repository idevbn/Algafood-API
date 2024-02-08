package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.in.PedidoInputDTO;
import com.algaworks.algafood.api.v1.model.out.PedidoOutputDTO;
import com.algaworks.algafood.api.v1.model.out.PedidoResumoOutputDTO;
import com.algaworks.algafood.core.springdoc.PageableParameter;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Pedidos")
@SecurityRequirement(name = "security_auth")
public interface PedidoControllerOpenApi {

    @PageableParameter
    @Operation(
            summary = "Pesquisa os pedidos",
            parameters = {
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "clienteId",
                            description = "ID do cliente para filtro da pesquisa",
                            example = "1",
                            schema = @Schema(type = "integer")
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "restauranteId",
                            description = "ID do restaurante para filtro da pesquisa",
                            example = "1",
                            schema = @Schema(type = "integer")
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "dataCriacaoInicio",
                            description = "Data/hora de criação inicial para filtro da pesquisa",
                            example = "2019-12-01T00:00:00Z",
                            schema = @Schema(type = "string", format = "date-time"
                            )
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY, name = "dataCriacaoFim",
                            description = "Data/hora de criação final para filtro da pesquisa",
                            example = "2019-12-02T23:59:59Z",
                            schema = @Schema(type = "string", format = "date-time")
                    )
            }
    )
    ResponseEntity<PagedModel<PedidoResumoOutputDTO>> pesquisar(
            @Parameter(hidden = true) final Pageable pageable,
            @Parameter(hidden = true) final PedidoFilter filtro
    );

    @Operation(summary = "Busca um pedido por código", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = {
                    @Content(schema = @Schema(ref = "ApiError"))}),
    })
    ResponseEntity<PedidoOutputDTO> adicionar(final PedidoInputDTO pedidoInput);

    ResponseEntity<PedidoOutputDTO> buscar(final String codigoPedido);

}
