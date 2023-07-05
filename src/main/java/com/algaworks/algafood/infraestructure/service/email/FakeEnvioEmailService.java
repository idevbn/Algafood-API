package com.algaworks.algafood.infraestructure.service.email;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEnvioEmailService extends SmtpEnvioEmailService {

    @Override
    public void enviar(final Mensagem mensagem) {
        // Foi necessário alterar o modificador de acesso do método processarTemplate
        // da classe pai para "protected", para poder chamar aqui
        final String corpo = this.processarTemplate(mensagem);

        log.info("[FAKE E-MAIL] Para: {}\n{}", mensagem.getDestinatarios(), corpo);
    }

}
