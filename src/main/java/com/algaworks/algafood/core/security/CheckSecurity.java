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
        @Retention(RUNTIME)
        @PreAuthorize("@algaSecurity.podeConsultarCozinhas()")
        @interface PodeConsultar {
        }
    }

    @interface Restaurantes {

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("@algaSecurity.podeGerenciarCadastroRestaurantes(#id)")
        @interface PodeGerenciarCadastro { }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("@algaSecurity.podeGerenciarFuncionamentoRestaurantes()")
        @interface PodeGerenciarFuncionamento { }

        @Retention(RUNTIME)
        @Target(METHOD)
        @PreAuthorize("@algaSecurity.podeConsultarRestaurantes()")
        @interface PodeConsultar { }
    }

    @interface Pedidos {

        @Target(METHOD)
        @Retention(RUNTIME)
        @PostAuthorize("hasAuthority('CONSULTAR_PEDIDOS') or "
                + "@algaSecurity.usuarioAutenticadoIgual(returnObject.body.cliente.id) or "
                + "@algaSecurity.gerenciaRestaurante(returnObject.body.restaurante.id)")
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @interface PodeBuscar { }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("@algaSecurity.podePesquisarPedidos(#filtro.clienteId, #filtro.restauranteId)")
        @interface PodePesquisar { }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
        @interface PodeCriar { }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("@algaSecurity.podeGerenciarPedidos(#codigoPedido)")
        @interface PodeGerenciarPedidos { }
    }

    @interface FormasPagamento {

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
        @interface PodeEditar { }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("@algaSecurity.podeConsultarFormasPagamento()")
        @interface PodeConsultar { }

    }

    @interface Cidades {

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_CIDADES')")
        @interface PodeEditar { }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("@algaSecurity.podeConsultarCidades()")
        @interface PodeConsultar { }

    }

    @interface Estados {

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_ESTADOS')")
        @interface PodeEditar { }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("@algaSecurity.podeConsultarEstados()")
        @interface PodeConsultar { }
    }

    @interface UsuariosGruposPermissoes {

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and "
                + "@algaSecurity.usuarioAutenticadoIgual(#usuarioId)")
        @interface PodeAlterarPropriaSenha { }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES') or "
                + "@algaSecurity.usuarioAutenticadoIgual(#usuarioId))")
        @interface PodeAlterarUsuario { }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("@algaSecurity.podeEditarUsuariosGruposPermissoes()")
        @interface PodeEditar { }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("@algaSecurity.podeConsultarUsuariosGruposPermissoes()")
        @interface PodeConsultar { }
    }

    @interface Estatisticas {

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("@algaSecurity.podeConsultarEstatisticas()")
        @interface PodeConsultar { }
    }

}
