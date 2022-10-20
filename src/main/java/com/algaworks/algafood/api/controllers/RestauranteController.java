package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> buscar(@PathVariable(value = "id") Long id) {

        try {
            Restaurante restauranteBuscado = this.service.buscar(id);

            ResponseEntity<Restaurante> restauranteResponse = ResponseEntity
                    .status(HttpStatus.OK)
                    .body(restauranteBuscado);

            return restauranteResponse;
        } catch (EntidadeNaoEncontradaException ex) {
            ResponseEntity<String> restauranteResponse = ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());

            return restauranteResponse;
        }
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
        try {
            Restaurante restauranteSalvo = this.service.salvar(restaurante);

            ResponseEntity<Restaurante> restauranteResponse = ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(restauranteSalvo);

            return restauranteResponse;
        } catch (EntidadeNaoEncontradaException ex) {
            ResponseEntity<String> restauranteResponse = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());

            return restauranteResponse;
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> atualizar(
            @PathVariable(value = "id") Long id,
            @RequestBody Restaurante restaurante
    ) {
        try {
            Restaurante restauranteAtual = this.service.buscar(id);

            BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento");

            Restaurante restauranteSalvo = this.service.salvar(restauranteAtual);

            ResponseEntity<Restaurante> restauranteResponse = ResponseEntity
                    .status(HttpStatus.OK)
                    .body(restauranteSalvo);

            return restauranteResponse;

        } catch (EntidadeNaoEncontradaException ex) {

            ResponseEntity<String> restauranteResponse = ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());

            return restauranteResponse;
        }
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> atualizarParcialmente(
            @PathVariable("id") Long id,
            @RequestBody Map<String, Object> campos
    ) {
        Restaurante restauranteAtual = this.service.buscar(id);

        if (restauranteAtual == null) {
            ResponseEntity<Restaurante> restauranteResponse = ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();

            return restauranteResponse;
        }

        merge(campos, restauranteAtual);

        ResponseEntity<?> atualizar = this.atualizar(id, restauranteAtual);

        return atualizar;
    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
        ObjectMapper objectMapper = new ObjectMapper();                                            
        Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

        dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            field.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

            ReflectionUtils.setField(field, restauranteDestino, novoValor);
        });
    }
}