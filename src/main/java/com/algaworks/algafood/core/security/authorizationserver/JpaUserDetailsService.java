package com.algaworks.algafood.core.security.authorizationserver;

import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Serviço responsável pela autenticação de um usuário
 *
 * @author Idevaldo Neto <idevbn@gmail.com>
 */
@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UsuarioRepository repository;

    @Autowired
    public JpaUserDetailsService(final UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {

        final Usuario usuario = this.repository.findByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuário não encontrado com email informado")
                );

        final User authUser = new User(
                usuario.getEmail(),
                usuario.getSenha(),
                this.getAuthorities(usuario)
        );

        return authUser;
    }

    private Collection<GrantedAuthority> getAuthorities(final Usuario usuario) {
        return usuario
                .getGrupos()
                .stream()
                .flatMap(grupo -> grupo.getPermissoes().stream())
                .map(permissao -> new SimpleGrantedAuthority(permissao
                        .getNome()
                        .toUpperCase())
                )
                .collect(Collectors.toSet());
    }

}
