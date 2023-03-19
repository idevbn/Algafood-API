package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmissaoPedidoService {

    private final PedidoRepository repository;

    @Autowired
    public EmissaoPedidoService(final PedidoRepository repository) {
        this.repository = repository;
    }

    public Pedido buscarOuFalhar(final Long id) {
        final Pedido pedido = this.repository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));

        return pedido;
    }

}
