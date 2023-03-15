package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.PermissaoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CadastroPermissaoService {

    private final PermissaoRepository repository;

    @Autowired
    public CadastroPermissaoService(final PermissaoRepository repository) {
        this.repository = repository;
    }

    public Permissao buscarOuFalhar(final Long id) {
        final Permissao permissao = this.repository.findById(id)
                .orElseThrow(() -> new PermissaoNaoEncontradaException(id));

        return permissao;
    }

    @Transactional
    public Permissao salvar(final Permissao permissao) {
        final Permissao permissaoSalva = this.repository.save(permissao);

        return permissaoSalva;
    }

}
