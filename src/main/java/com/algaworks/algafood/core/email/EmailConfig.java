package com.algaworks.algafood.core.email;

import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.infraestructure.service.email.FakeEnvioEmailService;
import com.algaworks.algafood.infraestructure.service.email.SandboxEnvioEmailService;
import com.algaworks.algafood.infraestructure.service.email.SmtpEnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    private final EmailProperties emailProperties;

    @Autowired
    public EmailConfig(final EmailProperties emailProperties) {
        this.emailProperties = emailProperties;
    }

    @Bean
    public EnvioEmailService envioEmailService() {

        switch (emailProperties.getImpl()) {
            case FAKE:
                return new FakeEnvioEmailService();
            case SMTP:
                return new SmtpEnvioEmailService();
            case SANDBOX:
                return new SandboxEnvioEmailService(emailProperties);
            default:
                return null;
        }
    }

}
