package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FluxoPedidoService {

    private final EmissaoPedidoService pedidoService;
    private final EnvioEmailService envioEmailService;

    @Autowired
    public FluxoPedidoService(final EmissaoPedidoService pedidoService,
                              final EnvioEmailService envioEmailService) {
        this.pedidoService = pedidoService;
        this.envioEmailService = envioEmailService;
    }

    @Transactional
    public void confirmar(final String codigo) {
        final Pedido pedido = this.pedidoService.buscarOuFalhar(codigo);

       pedido.confirmar();

        final EnvioEmailService.Mensagem mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado!")
                .corpo("O pedido de código " + pedido.getCodigo() + " foi confirmado")
                .destinatario(pedido.getCliente().getEmail())
                .build();

       this.envioEmailService.enviar(mensagem);
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
