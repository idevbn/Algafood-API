package com.algaworks.algafood.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/oauth2/**").authenticated()
                .and()
                .csrf().disable()
                .cors().and()
                .oauth2ResourceServer().jwt();

        return http
                .formLogin(Customizer.withDefaults())
                .build();

    }

}
