package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroCozinhaService {

    @Autowired
    CozinhaRepository repository;

    public Cozinha salvar(final Cozinha cozinha) {
        final Cozinha cozinhaSalva = this.repository.save(cozinha);

        return cozinhaSalva;
    }

    public void excluir(final Long id) {

        try {
            this.repository.deleteById(id);
        }
        catch (EmptyResultDataAccessException ex) {
            throw new CozinhaNaoEncontradaException(id);
        }
        catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException(
                    String.format("Cozinha de id=%d não pode ser removida pois está em uso", id)
            );
        }
    }

    public Cozinha buscarOuFalhar(final Long id) {
        final Cozinha cozinhaEncontrada = this.repository.findById(id).orElseThrow(
                () -> new CozinhaNaoEncontradaException(id)
        );

        return cozinhaEncontrada;
    }

}
