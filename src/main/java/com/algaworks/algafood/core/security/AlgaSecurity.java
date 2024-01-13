package com.algaworks.algafood.core.security;

import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final RestauranteRepository restauranteRepository;
    private final PedidoRepository pedidoRepository;
    @Autowired
    public AlgaSecurity(final RestauranteRepository restauranteRepository,
                        final PedidoRepository pedidoRepository) {
        this.restauranteRepository = restauranteRepository;
        this.pedidoRepository = pedidoRepository;
    }

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

    public boolean gerenciaRestaurante(final Long restauranteId) {
        if (restauranteId == null) {
            return false;
        }

        final boolean existsResponsavel = this.restauranteRepository
                .existsResponsavel(restauranteId, this.getUsuarioId());

        return existsResponsavel;
    }

    public boolean gerenciaRestauranteDoPedido(final String codigoPedido) {
        return pedidoRepository.isPedidoGerenciadoPor(codigoPedido, this.getUsuarioId());
    }

    public boolean usuarioAutenticadoIgual(final Long usuarioId) {
        return this.getUsuarioId() != null && usuarioId != null
                && this.getUsuarioId().equals(usuarioId);
    }

    /**
     * Método que verifica se há pelo menos uma authority com o nome fornecido.
     * Caso não exista nenhuma authority correspondente, é retornado false.
     */
    public boolean hasAuthority(final String authorityName) {
        return this.getAuthentication().getAuthorities()
                .stream()
                .anyMatch(authority -> authority.getAuthority().equals(authorityName));
    }

    public boolean podeGerenciarPedidos(final String codigoPedido) {
        return this.hasAuthority("SCOPE_WRITE")
                && (this.hasAuthority("GERENCIAR_PEDIDOS")
                || this.gerenciaRestauranteDoPedido(codigoPedido));
    }

}
