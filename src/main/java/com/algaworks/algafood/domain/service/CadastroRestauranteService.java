package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroRestauranteService {

    private static final String MSG_RESTAURANTE_NAO_ENCONTRADO = "Não existe um restaurante cadastrado com o id=%d";

    @Autowired
    private RestauranteRepository repository;

    @Autowired
    private CadastroCozinhaService cozinhaService;

    public Restaurante salvar(Restaurante restaurante) {
        final Long cozinhaId = restaurante.getCozinha().getId();

        final Cozinha cozinha = this.cozinhaService.buscarOuFalhar(cozinhaId);

        restaurante.setCozinha(cozinha);

        final Restaurante restauranteSalvo = this.repository.save(restaurante);

        return restauranteSalvo;
    }

    public Restaurante buscarOuFalhar(final Long id) {
        final Restaurante restauranteEncontrado = this.repository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, id)
                )
        );

        return restauranteEncontrado;
    }

}
