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

    private static final String MSG_COZINHA_NAO_ENCONTRADA = "Não existe um cadastro de cozinha com id=%d";

    @Autowired
    CozinhaRepository repository;

    public Cozinha salvar(Cozinha cozinha) {
        Cozinha cozinhaSalva = this.repository.save(cozinha);

        return cozinhaSalva;
    }

    public void excluir(Long id) {

        try {
            this.repository.deleteById(id);
        }
        catch (EmptyResultDataAccessException ex) {
            throw new EntidadeNaoEncontradaException(
                    String.format(MSG_COZINHA_NAO_ENCONTRADA, id)
            );
        }
        catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException(
                    String.format("Cozinha de id=%d não pode ser removida pois está em uso", id)
            );
        }
    }

    public Cozinha buscarOuFalhar(final Long id) {
        final Cozinha cozinhaEncontrada = this.repository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format(MSG_COZINHA_NAO_ENCONTRADA, id)
                )
        );

        return cozinhaEncontrada;
    }

}
