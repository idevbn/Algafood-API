package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroCozinhaService {

    @Autowired
    CozinhaRepository repository;

    public Cozinha salvar(Cozinha cozinha) {
        Cozinha cozinhaSalva = this.repository.salvar(cozinha);

        return cozinha;
    }
}
