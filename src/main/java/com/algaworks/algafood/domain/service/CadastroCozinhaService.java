package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
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

    public Cozinha salvar(Cozinha cozinha) {
        Cozinha cozinhaSalva = this.repository.salvar(cozinha);

        return cozinha;
    }

    public void excluir(Long id) {

        try {
            this.repository.remover(id);
        }
        catch (EmptyResultDataAccessException ex) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um cadastro de cozinho com id = %d", id)
            );
        }
        catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException(
                    String.format("Cozinha de id = %d não pode ser removida pois está em uso", id)
            );
        }
    }
}
