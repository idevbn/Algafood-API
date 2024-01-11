package com.algaworks.algafood.core.security;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public @interface CheckSecurity {

    @interface Cozinhas {
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHAS')")
        @interface PodeGerenciarCadastro {
        }

        @Target(METHOD)
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RUNTIME)
        @interface PodeConsultar {
        }
    }

    @interface Restaurantes {

        @Retention(RUNTIME)
        @Target(METHOD)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_RESTAURANTES')")
        @interface PodeGerenciarCadastro { }

        @Retention(RUNTIME)
        @Target(METHOD)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and "
                + "(hasAuthority('EDITAR_RESTAURANTES') or "
                + "@algaSecurity.gerenciaRestaurante(#id))")
        @interface PodeGerenciarFuncionamento { }

        @Retention(RUNTIME)
        @Target(METHOD)
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @interface PodeConsultar { }
    }

    @interface Pedidos {

        @Retention(RUNTIME)
        @Target(METHOD)
        @PostAuthorize("hasAuthority('CONSULTAR_PEDIDOS') or "
                + "@algaSecurity.getUsuarioId() == returnObject.body.cliente.id or "
                + "@algaSecurity.gerenciaRestaurante(returnObject.body.restaurante.id)")
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @interface PodeBuscar { }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_READ') and (hasAuthority('CONSULTAR_PEDIDOS') or "
                + "@algaSecurity.getUsuarioId() == #filtro.clienteId or"
                + "@algaSecurity.gerenciaRestaurante(#filtro.restauranteId))")
        @interface PodePesquisar { }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
        @interface PodeCriar { }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('GERENCIAR_PEDIDOS') or "
                + "@algaSecurity.gerenciaRestauranteDoPedido(#codigo))")
        @interface PodeGerenciarPedidos { }
    }

    @interface FormasPagamento {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeEditar { }

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar { }

    }

    @interface Cidades {

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_CIDADES')")
        @interface PodeEditar { }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @interface PodeConsultar { }

    }

    @interface Estados {

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_ESTADOS')")
        @interface PodeEditar { }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @interface PodeConsultar { }
    }

    @interface UsuariosGruposPermissoes {

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and "
                + "@algaSecurity.getUsuarioId() == #usuarioId")
        @interface PodeAlterarPropriaSenha { }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES') or "
                + "@algaSecurity.getUsuarioId() == #usuarioId)")
        @interface PodeAlterarUsuario { }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES')")
        @interface PodeEditar { }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('CONSULTAR_USUARIOS_GRUPOS_PERMISSOES')")
        @interface PodeConsultar { }
    }

}
