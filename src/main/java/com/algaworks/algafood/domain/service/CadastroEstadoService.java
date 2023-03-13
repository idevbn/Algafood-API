package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CadastroEstadoService {

    private static final String MSG_ESTADO_EM_USO = "Estado de id=%d não pode ser removido pois está em uso";

    private final EstadoRepository repository;

    @Autowired
    public CadastroEstadoService(final EstadoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Estado salvar(final Estado estado) {
        final Estado estadoSalvo = this.repository.save(estado);

        return estadoSalvo;
    }

    @Transactional
    public void excluir(final Long id) {
        try {
            this.repository.deleteById(id);
            this.repository.flush();
        } catch (final EmptyResultDataAccessException ex) {
            throw new EstadoNaoEncontradoException(id);
        } catch (final DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_ESTADO_EM_USO, id)
            );
        }
    }
    
    public Estado buscarOuFalhar(final Long id) {
        final Estado estadoEncontrado = this.repository.findById(id).orElseThrow(
                () -> new EstadoNaoEncontradoException(id)
        );

        return estadoEncontrado;
    }
    
}
