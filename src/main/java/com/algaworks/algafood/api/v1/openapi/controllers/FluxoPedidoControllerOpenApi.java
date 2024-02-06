package com.algaworks.algafood.api.v1.openapi.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
public interface FluxoPedidoControllerOpenApi {

    ResponseEntity<Void> confirmar(final String codigoPedido);

    ResponseEntity<Void> cancelar(final String codigoPedido);

    ResponseEntity<Void> entregar(final String codigoPedido);

}
