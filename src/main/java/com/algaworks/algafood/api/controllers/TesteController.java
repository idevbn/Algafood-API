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

    private final CozinhaRepository repository;
    private final RestauranteRepository restauranteRepository;

    @Autowired
    public TesteController(final CozinhaRepository repository,
                           final RestauranteRepository restauranteRepository) {
        this.repository = repository;
        this.restauranteRepository = restauranteRepository;
    }

    @GetMapping(value = "/cozinhas/por-nome")
    public ResponseEntity<List<Cozinha>> cozinhasPorNome(@RequestParam final String nome) {
        final List<Cozinha> cozinhas = this.repository.findTodasByNomeContaining(nome);

        final ResponseEntity<List<Cozinha>> cozinhasResponse = ResponseEntity
                .status(HttpStatus.OK)
                .body(cozinhas);

        return cozinhasResponse;
    }

    @GetMapping(value = "/cozinhas/unica-por-nome")
    public ResponseEntity<Optional<Cozinha>> cozinhaPorNome(@RequestParam final String nome) {
        final Optional<Cozinha> cozinha = this.repository.findByNome(nome);

        final ResponseEntity<Optional<Cozinha>> cozinhaResponse = ResponseEntity
                .status(HttpStatus.OK)
                .body(cozinha);

        return cozinhaResponse;
    }

    @GetMapping(value = "/restaurantes/por-taxa-frete")
    public ResponseEntity<List<Restaurante>> restaurantesPorTaxaFrete(
            @RequestParam final BigDecimal taxaInicial,
            @RequestParam final BigDecimal taxaFinal
    ) {
        final List<Restaurante> restaurantes = this.restauranteRepository
                .findByTaxaFreteBetween(taxaInicial, taxaFinal);

        final ResponseEntity<List<Restaurante>> restaurantesResponse = ResponseEntity
                .status(HttpStatus.OK)
                .body(restaurantes);

        return restaurantesResponse;
    }

    @GetMapping(value = "/restaurantes/por-nome")
    public ResponseEntity<List<Restaurante>> restaurantesPorNome(
            @RequestParam final String nome,
            @RequestParam final Long cozinhaId
    ) {
        final List<Restaurante> restaurantes = this.restauranteRepository
                .consultarPorNome(nome, cozinhaId);

        final ResponseEntity<List<Restaurante>> restaurantesResponse = ResponseEntity
                .status(HttpStatus.OK)
                .body(restaurantes);

        return restaurantesResponse;
    }

    @GetMapping(value = "/restaurantes/primeiro-por-nome")
    public ResponseEntity<Optional<Restaurante>> restaurantePrimeiroPorNome(
            @RequestParam final String nome
    ) {
        final Optional<Restaurante> restaurante = this.restauranteRepository
                .findFirstRestauranteByNomeContaining(nome);

        final ResponseEntity<Optional<Restaurante>> restauranteResponse = ResponseEntity
                .status(HttpStatus.OK)
                .body(restaurante);

        return restauranteResponse;
    }

    @GetMapping(value = "/restaurantes/top2-por-nome")
    public ResponseEntity<List<Restaurante>> restaurantesTop2PorNome(
            @RequestParam final String nome
    ) {
        final List<Restaurante> restaurantes = this.restauranteRepository
                .findTop2ByNomeContaining(nome);

        final ResponseEntity<List<Restaurante>> restaurantesResponse = ResponseEntity
                .status(HttpStatus.OK)
                .body(restaurantes);

        return restaurantesResponse;
    }

    @GetMapping(value = "/restaurantes/por-nome-e-frete")
    public ResponseEntity<List<Restaurante>> restaurantesPorNomeEFrete(
            @RequestParam(required = false) final String nome,
            @RequestParam(required = false) final BigDecimal taxaFreteInicial,
            @RequestParam(required = false) final BigDecimal taxaFreteFinal
    ) {
        final List<Restaurante> restaurantes = this.restauranteRepository
                .find(nome, taxaFreteInicial, taxaFreteFinal);

        final ResponseEntity<List<Restaurante>> restaurantesResponse = ResponseEntity
                .status(HttpStatus.OK)
                .body(restaurantes);

        return restaurantesResponse;
    }

    @GetMapping(value = "/restaurantes/com-frete-gratis")
    public ResponseEntity<List<Restaurante>> restaurantesComFreteGratis(
            @RequestParam(required = false) final String nome
    ) {
        final List<Restaurante> restaurantes = this.restauranteRepository
                .findComFreteGratis(nome);

        final ResponseEntity<List<Restaurante>> restaurantesResponse = ResponseEntity
                .status(HttpStatus.OK)
                .body(restaurantes);

        return restaurantesResponse;
    }

    @GetMapping(value = "/restaurantes/buscar-primeiro")
    public ResponseEntity<Optional<Restaurante>> restauranteBuscarPrimeiro() {
        final Optional<Restaurante> restaurante = this.restauranteRepository.buscarPrimeiro();

        final ResponseEntity<Optional<Restaurante>> restauranteResponse = ResponseEntity
                .status(HttpStatus.OK)
                .body(restaurante);

        return restauranteResponse;
    }

}
