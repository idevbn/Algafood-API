package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.domain.service.FluxoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/pedidos/{id}")
public class FluxoPedidoController {

    private final FluxoPedidoService pedidoService;

    @Autowired
    public FluxoPedidoController(final FluxoPedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PutMapping(value = "/confirmacao")
    public ResponseEntity<Void> confirmar(@PathVariable("id") final Long id) {
        this.pedidoService.confirmar(id);

        final ResponseEntity<Void> response = ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

        return response;
    }

    @PutMapping(value = "/cancelamento")
    public ResponseEntity<Void> cancelar(@PathVariable("id") final Long id) {
        this.pedidoService.cancelar(id);

        final ResponseEntity<Void> response = ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

        return response;
    }

    @PutMapping(value = "/entrega")
    public ResponseEntity<Void> entregar(@PathVariable("id") final Long id) {
        this.pedidoService.entregar(id);

        final ResponseEntity<Void> response = ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

        return response;
    }

}
