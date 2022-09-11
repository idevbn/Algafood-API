package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository repository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public List<Restaurante> listar() {
        List<Restaurante> restaurantes = this.repository.listar();

        return restaurantes;
    }

    public Restaurante buscar(Long id) {
        Restaurante restauranteBuscado = this.repository.buscar(id);

        return restauranteBuscado;
    }

    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = this.cozinhaRepository.buscar(cozinhaId);

        if (cozinha == null) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe uma cozinha cadastrada com o id=%d", cozinhaId)
            );
        }

        restaurante.setCozinha(cozinha);

        Restaurante restauranteSalvo = this.repository.salvar(restaurante);

        return restauranteSalvo;
    }
}
