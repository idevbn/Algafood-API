package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class CadastroGrupoService {

    private static final String MSG_GRUPO_EM_USO
            = "Grupo de código %d não pode ser removido, pois está em uso";

    private final GrupoRepository repository;
    private final CadastroPermissaoService permissaoService;

    @Autowired
    public CadastroGrupoService(final GrupoRepository repository,
                                final CadastroPermissaoService permissaoService) {
        this.repository = repository;
        this.permissaoService = permissaoService;
    }

    @Transactional
    public Grupo salvar(final Grupo grupo) {
        final Grupo grupoSalvo = this.repository.save(grupo);

        return grupoSalvo;
    }

    public Grupo buscarOuFalhar(final Long id) {
        final Grupo grupoEncontrado = this.repository.findById(id).orElseThrow(
                () -> new GrupoNaoEncontradoException(id)
        );

        return grupoEncontrado;
    }

    @Transactional
    public void excluir(final Long id) {

        try {
            this.repository.deleteById(id);
            this.repository.flush();
        } catch (final EmptyResultDataAccessException ex) {
            throw new GrupoNaoEncontradoException(id);
        } catch (final DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_GRUPO_EM_USO, id)
            );
        }
    }

    @Transactional
    public void associarPermissao(final Long grupoId,
                                  final Long permissaoId) {
        final Grupo grupo = this.buscarOuFalhar(grupoId);

        final Permissao permissao = this.permissaoService.buscarOuFalhar(permissaoId);

        grupo.adicionarPermissao(permissao);
    }

    @Transactional
    public void desassociarPermissao(final Long grupoId,
                                     final Long permissaoId) {
        final Grupo grupo = this.buscarOuFalhar(grupoId);

        final Permissao permissao = this.permissaoService.buscarOuFalhar(permissaoId);

        grupo.removerPermissao(permissao);
    }

}
