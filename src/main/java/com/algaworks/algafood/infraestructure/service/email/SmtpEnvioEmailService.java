package com.algaworks.algafood.infraestructure.service.email;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Service
public class SmtpEnvioEmailService implements EnvioEmailService {

    private final JavaMailSender mailSender;

    private final EmailProperties emailProperties;

    private final Configuration freemarkerConfig;

    @Autowired
    public SmtpEnvioEmailService(final JavaMailSender mailSender,
                                 final EmailProperties emailProperties,
                                 final Configuration freemarkerConfig) {
        this.mailSender = mailSender;
        this.emailProperties = emailProperties;
        this.freemarkerConfig = freemarkerConfig;
    }

    @Override
    public void enviar(final Mensagem mensagem) {

        try {
            final String corpo = this.processarTemplate(mensagem);

            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();

            final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setFrom(this.emailProperties.getRemetente());
            helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
            helper.setSubject(mensagem.getAssunto());
            helper.setText(corpo, true);

            this.mailSender.send(mimeMessage);
        } catch (final Exception e) {
            throw new EmailException("Não foi possível enviar email", e);
        }
    }

    private String processarTemplate(final Mensagem mensagem) {

        try {
            final Template template = this.freemarkerConfig.getTemplate(mensagem.getCorpo());

            final String corpo = FreeMarkerTemplateUtils
                    .processTemplateIntoString(template, mensagem.getVariaveis());

            return corpo;
        } catch (final Exception e) {
            throw new EmailException("Não foi possível montar o template do email", e);
        }
    }

}
