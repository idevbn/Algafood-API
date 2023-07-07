package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoClientePedidoConfirmadoListener {

    private final EnvioEmailService envioEmailService;

    @Autowired
    public NotificacaoClientePedidoConfirmadoListener(final EnvioEmailService envioEmailService) {
        this.envioEmailService = envioEmailService;
    }

    @EventListener
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
