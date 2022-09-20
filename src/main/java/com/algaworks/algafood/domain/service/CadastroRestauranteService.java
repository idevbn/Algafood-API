package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository repository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public List<Restaurante> listar() {
        List<Restaurante> restaurantes = this.repository.findAll();

        return restaurantes;
    }

    public Restaurante buscar(Long id) {
        Optional<Restaurante> restauranteBuscado = this.repository.findById(id);

        if (restauranteBuscado.isEmpty()) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um restaurante cadastrado com o id=%d", id)
            );
        }

        Long cozinhaId = restauranteBuscado.get().getCozinha().getId();
        Optional<Cozinha> cozinha = this.cozinhaRepository.findById(cozinhaId);

        if (cozinha.isEmpty()) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe uma cozinha cadastrada com o id=%d", cozinhaId)
            );
        }

        return restauranteBuscado.get();
    }

    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = this.cozinhaRepository.findById(cozinhaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Não existe uma cozinha cadastrada com o id=%d", cozinhaId)
                ));

        restaurante.setCozinha(cozinha);

        Restaurante restauranteSalvo = this.repository.save(restaurante);

        return restauranteSalvo;
    }
}
