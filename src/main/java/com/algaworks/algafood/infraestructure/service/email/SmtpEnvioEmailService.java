package com.algaworks.algafood.infraestructure.service.email;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class SmtpEnvioEmailService implements EnvioEmailService {

    private final JavaMailSender mailSender;

    private final EmailProperties emailProperties;

    @Autowired
    public SmtpEnvioEmailService(final JavaMailSender mailSender,
                                 final EmailProperties emailProperties) {
        this.mailSender = mailSender;
        this.emailProperties = emailProperties;
    }

    @Override
    public void enviar(final Mensagem mensagem) {

        try {
            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();

            final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setFrom(this.emailProperties.getRemetente());
            helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
            helper.setSubject(mensagem.getAssunto());
            helper.setText(mensagem.getCorpo(), true);

            this.mailSender.send(mimeMessage);
        } catch (final Exception e) {
            throw new EmailException("Não foi possível enviar email", e);
        }
    }

}
