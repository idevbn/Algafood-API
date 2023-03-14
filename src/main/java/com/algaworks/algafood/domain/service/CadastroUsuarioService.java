package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CadastroUsuarioService {

    private final UsuarioRepository repository;

    @Autowired
    public CadastroUsuarioService(final UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario buscarOuFalhar(final Long id) {
        final Usuario usuarioEncontrado = this.repository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));

        return usuarioEncontrado;
    }

    @Transactional
    public Usuario salvar(final Usuario usuario) {
        this.repository.detach(usuario);

        final Optional<Usuario> usuarioExistente = this.repository
                .findByEmail(usuario.getEmail());

        if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
            throw new NegocioException(
                    String.format("Já existe um usuário cadastrado com o email %s", usuario.getEmail())
            );
        }

        final Usuario usuarioSalvo = this.repository.save(usuario);

        return usuarioSalvo;
    }

    @Transactional
    public void alterarSenha(final Long id, final String senhaAtual, final String novaSenha) {
        final Usuario usuarioEncontrado = this.buscarOuFalhar(id);

        if (usuarioEncontrado.senhaNaoCoincideCom(senhaAtual)) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }

        usuarioEncontrado.setSenha(novaSenha);
    }

}