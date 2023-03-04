package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository repository;

    @Autowired
    private CadastroCozinhaService cozinhaService;

    @Transactional
    public Restaurante salvar(final Restaurante restaurante) {
        final Long cozinhaId = restaurante.getCozinha().getId();

        final Cozinha cozinha = this.cozinhaService.buscarOuFalhar(cozinhaId);

        restaurante.setCozinha(cozinha);

        final Restaurante restauranteSalvo = this.repository.save(restaurante);

        return restauranteSalvo;
    }

    public Restaurante buscarOuFalhar(final Long id) {
        final Restaurante restauranteEncontrado = this.repository.findById(id).orElseThrow(
                () -> new RestauranteNaoEncontradoException(id)
        );

        return restauranteEncontrado;
    }

}
