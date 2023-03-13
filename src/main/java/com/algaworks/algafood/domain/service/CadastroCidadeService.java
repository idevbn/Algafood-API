package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CadastroCidadeService {

    private static final String MSG_CIDADE_EM_USO = "Cidade com id=%d não pode ser removida, pois está em uso";

    private final CidadeRepository repository;
    private final CadastroEstadoService estadoService;

    @Autowired
    public CadastroCidadeService(final CidadeRepository repository,
                                 final CadastroEstadoService estadoService) {
        this.repository = repository;
        this.estadoService = estadoService;
    }

    @Transactional
    public Cidade salvar(final Cidade cidade) {
        final Long estadoId = cidade.getEstado().getId();

        final Estado estado = this.estadoService.buscarOuFalhar(estadoId);

        cidade.setEstado(estado);

        final Cidade cidadeSalva = this.repository.save(cidade);

        return cidadeSalva;
    }

    @Transactional
    public void excluir(final Long id) {
        try {
            this.repository.deleteById(id);
            this.repository.flush();
        } catch (final EmptyResultDataAccessException ex) {
            throw new CidadeNaoEncontradaException(id);
        } catch (final DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_CIDADE_EM_USO, id));
        }
    }

    public Cidade buscarOuFalhar(final Long id) {
        final Cidade cidadeEncontrada = this.repository.findById(id).orElseThrow(
                () -> new CidadeNaoEncontradaException(id)
        );

        return cidadeEncontrada;
    }

}
