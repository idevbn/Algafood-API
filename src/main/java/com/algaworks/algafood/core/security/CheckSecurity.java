package com.algaworks.algafood.core.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface CheckSecurity {

    @interface Cozinhas {
        @Retention(RetentionPolicy.RUNTIME)
        @PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
        @interface PodeEditar {
        }

        @Target(ElementType.METHOD)
        @PreAuthorize("isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @interface PodeConsultar {
        }
    }

}
