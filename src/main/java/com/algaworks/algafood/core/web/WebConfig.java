package com.algaworks.algafood.core.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.Filter;

/**
 * Classe de configuração global do CORS
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ApiRetirementHandler apiRetirementHandler;

    @Autowired
    public WebConfig(final ApiRetirementHandler apiRetirementHandler) {
        this.apiRetirementHandler = apiRetirementHandler;
    }

    /**
     * Recebe uma requisição HTTP e momento de informar a resposta HTTP,
     * é gerado um hash com o cabeçalho ETag.
     * <p>
     * É verificado se o hash da resposta coincide com a ETag 'If-None-Match'
     * do cabeçalho que vem da requisição.
     * <p>
     * Se a hash da resposta e o valor da ETag da requisição coincidirem é
     * retornado um status code HTTP 304 - Not Modified
     * <p>
     * Se o hash e a ETag diferirem é criado nova ETag.
     */
    @Bean
    public Filter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }

}
