package com.algaworks.algafood.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Classe responsável pela obtenção dos dados do
 * usuário autenticado dinamicamente via token JWT.
 *
 * @author Idevaldo Neto <idevbn@gmail.com>
 */
@Component
public class AlgaSecurity {

    public Authentication getAuthentication() {
        final Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        return authentication;
    }

    public Long getUsuarioId() {
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();

        /**
         * Essa não é uma solução definitiva e ideal.
         * Deve-se buscar uma alternativa válida.
         *
         * OBS: fica a dívida técnica.
         */
        final List<Object> claimsValues = jwt
                .getClaims()
                .values()
                .stream()
                .toList();

        final Long userId =  (Long) claimsValues.get(0);

        return userId;
    }

}
