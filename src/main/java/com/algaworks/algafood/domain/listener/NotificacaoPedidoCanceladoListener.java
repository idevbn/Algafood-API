package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoPedidoCanceladoListener {

    private final EnvioEmailService envioEmailService;

    @Autowired
    public NotificacaoPedidoCanceladoListener(final EnvioEmailService envioEmailService) {
        this.envioEmailService = envioEmailService;
    }

    @TransactionalEventListener
    public void aoCancelarPedido(final PedidoCanceladoEvent event) {

        final Pedido pedido = event.getPedido();

        final EnvioEmailService.Mensagem mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido cancelado")
                .corpo("emails/pedido-cancelado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        this.envioEmailService.enviar(mensagem);
    }

}
