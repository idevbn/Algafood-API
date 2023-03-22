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
    public void confirmar(final String codigo) {
        final Pedido pedido = this.pedidoService.buscarOuFalhar(codigo);

       pedido.confirmar();
    }

    @Transactional
    public void cancelar(final String codigo) {
        final Pedido pedido = this.pedidoService.buscarOuFalhar(codigo);

        pedido.cancelar();
    }

    @Transactional
    public void entregar(final String codigo) {
        final Pedido pedido = this.pedidoService.buscarOuFalhar(codigo);

        pedido.entregar();
    }

}
