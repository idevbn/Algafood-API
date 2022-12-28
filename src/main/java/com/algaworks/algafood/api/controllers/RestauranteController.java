package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository repository;

    @Autowired
    private CadastroRestauranteService service;

    @GetMapping
    public List<Restaurante> listar() {
        final List<Restaurante> restaurantes = this.repository.findAll();

        return restaurantes;
    }

    @GetMapping(value = "/{id}")
    public Restaurante buscar(@PathVariable(value = "id") final Long id) {
        final Restaurante restauranteEncontrado = this.service.buscarOuFalhar(id);

        return restauranteEncontrado;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Restaurante adicionar(@RequestBody final Restaurante restaurante) {
        final Restaurante restauranteSalvo = this.service.salvar(restaurante);

        return restauranteSalvo;
    }

    @PutMapping(value = "/{id}")
    public Restaurante atualizar(
            @PathVariable(value = "id") final Long id,
            @RequestBody final Restaurante restaurante
    ) {
        final Restaurante restauranteAtual = this.service.buscarOuFalhar(id);

        BeanUtils.copyProperties(
                restaurante,
                restauranteAtual,
                "id", "formasPagamento", "endereco","dataCadastro"
        );

        final Restaurante restauranteSalvo = this.service.salvar(restauranteAtual);

        return restauranteSalvo;
    }

    @PatchMapping(value = "/{id}")
    public Restaurante atualizarParcialmente(
            @PathVariable("id") final Long id,
            @RequestBody final Map<String, Object> campos
    ) {
        final Restaurante restauranteAtual = this.service.buscarOuFalhar(id);

        merge(campos, restauranteAtual);

        final Restaurante atualizar = this.atualizar(id, restauranteAtual);

        return atualizar;
    }

    private void merge(final Map<String, Object> dadosOrigem, final Restaurante restauranteDestino) {
        final ObjectMapper objectMapper = new ObjectMapper();

        final Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

        dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            field.setAccessible(true);

            final Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

            ReflectionUtils.setField(field, restauranteDestino, novoValor);
        });
    }

}