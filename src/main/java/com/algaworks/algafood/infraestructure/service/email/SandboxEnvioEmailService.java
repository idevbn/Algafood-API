package com.algaworks.algafood.infraestructure.service.email;

import com.algaworks.algafood.core.email.EmailProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

public class SandboxEnvioEmailService extends SmtpEnvioEmailService {

    private final EmailProperties emailProperties;

    @Autowired
    public SandboxEnvioEmailService(final EmailProperties emailProperties) {
        this.emailProperties = emailProperties;
    }

    @Override
    protected MimeMessage criarMimeMessage(final Mensagem mensagem) throws MessagingException {
        final MimeMessage mimeMessage = super.criarMimeMessage(mensagem);

        final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setTo(emailProperties.getSandbox().getDestinatario());

        return mimeMessage;
    }

}
