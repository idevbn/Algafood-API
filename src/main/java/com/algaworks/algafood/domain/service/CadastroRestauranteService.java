package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository repository;

    public List<Restaurante> listar() {
        List<Restaurante> restaurantes = this.repository.listar();

        return restaurantes;
    }

    public Restaurante buscar(Long id) {
        Restaurante restauranteBuscado = this.repository.buscar(id);

        return restauranteBuscado;
    }
}
