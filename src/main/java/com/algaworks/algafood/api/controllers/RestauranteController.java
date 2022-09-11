package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

    @Autowired
    private CadastroRestauranteService service;

    @GetMapping
    public ResponseEntity<List<Restaurante>> listar() {
        List<Restaurante> restaurantes = this.service.listar();

        ResponseEntity<List<Restaurante>> restaurantesResponse = ResponseEntity
                .status(HttpStatus.OK)
                .body(restaurantes);

        return restaurantesResponse;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Restaurante> buscar(@PathVariable(value = "id") Long id) {
        Restaurante restauranteBuscado = this.service.buscar(id);

        if (restauranteBuscado == null) {
            ResponseEntity<Restaurante> restauranteResponse = ResponseEntity
                   .status(HttpStatus.NOT_FOUND)
                    .build();

            return restauranteResponse;
        }

        ResponseEntity<Restaurante> restauranteResponse = ResponseEntity
                .status(HttpStatus.OK)
                .body(restauranteBuscado);

        return restauranteResponse;
    }
}
