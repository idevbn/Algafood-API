package com.algaworks.algafood.core.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(this.jwtAuthenticationConverter());
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        final JwtAuthenticationConverter jwtAuthenticationConverter
                = new JwtAuthenticationConverter();

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            List<String> authorities = jwt.getClaimAsStringList("authorities");

            if (authorities == null) {
                authorities = Collections.emptyList();
            }

            return authorities
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        });

        return jwtAuthenticationConverter;
    }

}
