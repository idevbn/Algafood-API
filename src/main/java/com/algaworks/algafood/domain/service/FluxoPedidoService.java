package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class FluxoPedidoService {

    private final EmissaoPedidoService pedidoService;
    private final PedidoRepository pedidoRepository;

    @Autowired
    public FluxoPedidoService(final EmissaoPedidoService pedidoService,
                              final PedidoRepository pedidoRepository) {
        this.pedidoService = pedidoService;
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional
    public void confirmar(final String codigo) {
        final Pedido pedido = this.pedidoService.buscarOuFalhar(codigo);

       pedido.confirmar();

       this.pedidoRepository.save(pedido);
    }

    @Transactional
    public void cancelar(final String codigo) {
        final Pedido pedido = this.pedidoService.buscarOuFalhar(codigo);

        pedido.cancelar();

        this.pedidoRepository.save(pedido);
    }

    @Transactional
    public void entregar(final String codigo) {
        final Pedido pedido = this.pedidoService.buscarOuFalhar(codigo);

        pedido.entregar();
    }

}
