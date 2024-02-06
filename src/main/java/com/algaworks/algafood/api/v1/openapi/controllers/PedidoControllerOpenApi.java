package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.in.PedidoInputDTO;
import com.algaworks.algafood.api.v1.model.out.PedidoOutputDTO;
import com.algaworks.algafood.api.v1.model.out.PedidoResumoOutputDTO;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
public interface PedidoControllerOpenApi {

    ResponseEntity<PagedModel<PedidoResumoOutputDTO>> pesquisar(final Pageable pageable,
                                                                final PedidoFilter filtro);

    ResponseEntity<PedidoOutputDTO> adicionar(final PedidoInputDTO pedidoInput);

    ResponseEntity<PedidoOutputDTO> buscar(final String codigoPedido);

}
