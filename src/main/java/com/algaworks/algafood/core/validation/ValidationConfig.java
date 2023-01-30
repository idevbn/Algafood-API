package com.algaworks.algafood.core.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfig {

    @Bean
    public LocalValidatorFactoryBean validator(final MessageSource messageSource) {
        final LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();

        bean.setValidationMessageSource(messageSource);

        return bean;
    }

}
