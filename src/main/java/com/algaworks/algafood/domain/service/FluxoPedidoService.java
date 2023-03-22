package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FluxoPedidoService {

    private final EmissaoPedidoService pedidoService;

    @Autowired
    public FluxoPedidoService(final EmissaoPedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Transactional
    public void confirmar(final Long pedidoId) {
        final Pedido pedido = this.pedidoService.buscarOuFalhar(pedidoId);

       pedido.confirmar();
    }

    @Transactional
    public void cancelar(final Long pedidoId) {
        final Pedido pedido = this.pedidoService.buscarOuFalhar(pedidoId);

        pedido.cancelar();
    }

    @Transactional
    public void entregar(final Long pedidoId) {
        final Pedido pedido = this.pedidoService.buscarOuFalhar(pedidoId);

        pedido.entregar();
    }

}
