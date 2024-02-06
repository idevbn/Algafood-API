package com.algaworks.algafood.api.v1.openapi.controllers;

import org.springframework.http.ResponseEntity;

public interface FluxoPedidoControllerOpenApi {

    ResponseEntity<Void> confirmar(final String codigoPedido);

    ResponseEntity<Void> cancelar(final String codigoPedido);

    ResponseEntity<Void> entregar(final String codigoPedido);

}
