package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/teste")
public class TesteController {

    @Autowired
    private CozinhaRepository repository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping(value = "/cozinhas/por-nome")
    public ResponseEntity<List<Cozinha>> cozinhasPorNome(@RequestParam String nome) {
        List<Cozinha> cozinhas = this.repository.findTodasByNomeContaining(nome);

        ResponseEntity<List<Cozinha>> cozinhasResponse = ResponseEntity
                .status(HttpStatus.OK)
                .body(cozinhas);

        return cozinhasResponse;
    }

    @GetMapping(value = "/cozinhas/unica-por-nome")
    public ResponseEntity<Optional<Cozinha>> cozinhaPorNome(@RequestParam String nome) {
        Optional<Cozinha> cozinha = this.repository.findByNome(nome);

        ResponseEntity<Optional<Cozinha>> cozinhaResponse = ResponseEntity
                .status(HttpStatus.OK)
                .body(cozinha);

        return cozinhaResponse;
    }

    @GetMapping(value = "/restaurantes/por-taxa-frete")
    public ResponseEntity<List<Restaurante>> restaurantesPorTaxaFrete(
            @RequestParam BigDecimal taxaInicial,
            @RequestParam BigDecimal taxaFinal
    ) {
        List<Restaurante> restaurantes = this.restauranteRepository
                .findByTaxaFreteBetween(taxaInicial, taxaFinal);

        ResponseEntity<List<Restaurante>> restaurantesResponse = ResponseEntity
                .status(HttpStatus.OK)
                .body(restaurantes);

        return restaurantesResponse;
    }

    @GetMapping(value = "/restaurantes/por-nome")
    public ResponseEntity<List<Restaurante>> restaurantesPorNome(
            @RequestParam String nome,
            @RequestParam Long cozinhaId
    ) {
        List<Restaurante> restaurantes = this.restauranteRepository
                .consultarPorNome(nome, cozinhaId);

        ResponseEntity<List<Restaurante>> restaurantesResponse = ResponseEntity
                .status(HttpStatus.OK)
                .body(restaurantes);

        return restaurantesResponse;
    }

    @GetMapping(value = "/restaurantes/primeiro-por-nome")
    public ResponseEntity<Optional<Restaurante>> restaurantePrimeiroPorNome(
            @RequestParam String nome
    ) {
        Optional<Restaurante> restaurante = this.restauranteRepository
                .findFirstRestauranteByNomeContaining(nome);

        ResponseEntity<Optional<Restaurante>> restauranteResponse = ResponseEntity
                .status(HttpStatus.OK)
                .body(restaurante);

        return restauranteResponse;
    }

    @GetMapping(value = "/restaurantes/top2-por-nome")
    public ResponseEntity<List<Restaurante>> restaurantesTop2PorNome(
            @RequestParam String nome
    ) {
        List<Restaurante> restaurantes = this.restauranteRepository
                .findTop2ByNomeContaining(nome);

        ResponseEntity<List<Restaurante>> restaurantesResponse = ResponseEntity
                .status(HttpStatus.OK)
                .body(restaurantes);

        return restaurantesResponse;
    }

    @GetMapping(value = "/restaurantes/por-nome-e-frete")
    public ResponseEntity<List<Restaurante>> restaurantesPorNomeEFrete(
            @RequestParam String nome,
            @RequestParam BigDecimal taxaFreteInicial,
            @RequestParam BigDecimal taxaFreteFinal
    ) {
        List<Restaurante> restaurantes = this.restauranteRepository
                .find(nome, taxaFreteInicial, taxaFreteFinal);

        ResponseEntity<List<Restaurante>> restaurantesResponse = ResponseEntity
                .status(HttpStatus.OK)
                .body(restaurantes);

        return restaurantesResponse;
    }
}
