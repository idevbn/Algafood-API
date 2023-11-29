package com.algaworks.algafood.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .cors()
                .and()
                .oauth2ResourceServer()
                .jwt();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        final SecretKeySpec secretKey =
                new SecretKeySpec("aa27b16ae5be4cdf90d9c37a35f9298a".getBytes(),
                        "HmacSHA256");

        return NimbusJwtDecoder
                .withSecretKey(secretKey)
                .build();
    }

}
