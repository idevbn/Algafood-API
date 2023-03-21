package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.enums.StatusPedido;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;

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

        if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {
            throw new NegocioException(
                    String.format("Status do pedido %d não pode ser alterado de %s para %s",
                            pedido.getId(),
                            pedido.getStatus().getDescricao(),
                            StatusPedido.CONFIRMADO.getDescricao())
            );
        }

        pedido.setStatus(StatusPedido.CONFIRMADO);
        pedido.setDataConfirmacao(OffsetDateTime.now());
    }

}
