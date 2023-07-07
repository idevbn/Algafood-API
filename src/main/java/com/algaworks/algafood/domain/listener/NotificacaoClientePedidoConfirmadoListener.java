package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoClientePedidoConfirmadoListener {

    private final EnvioEmailService envioEmailService;

    @Autowired
    public NotificacaoClientePedidoConfirmadoListener(final EnvioEmailService envioEmailService) {
        this.envioEmailService = envioEmailService;
    }

    @TransactionalEventListener
    public void aoConfirmarPedido(final PedidoConfirmadoEvent event) {

        final Pedido pedido = event.getPedido();

        final EnvioEmailService.Mensagem mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado!")
                .corpo("pedido-confirmado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        this.envioEmailService.enviar(mensagem);
    }

}
