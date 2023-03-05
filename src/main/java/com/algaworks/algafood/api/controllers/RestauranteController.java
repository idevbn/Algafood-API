package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository repository;

    @Autowired
    private CadastroRestauranteService service;

    @Autowired
    private SmartValidator validator;

    @GetMapping
    public List<RestauranteModel> listar() {
        final List<Restaurante> restaurantes = this.repository.findAll();

        final List<RestauranteModel> restaurantesModels = this.toCollectionModel(restaurantes);

        return restaurantesModels;
    }

    @GetMapping(value = "/{id}")
    public RestauranteModel buscar(@PathVariable(value = "id") final Long id) {
        final Restaurante restauranteEncontrado = this.service.buscarOuFalhar(id);

        final RestauranteModel restauranteModel = this.toModel(restauranteEncontrado);

        return restauranteModel;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public RestauranteModel adicionar(
            @RequestBody @Valid final Restaurante restaurante
    ) {
        try {
            final Restaurante restauranteSalvo = this.service.salvar(restaurante);

            final RestauranteModel restauranteModel = this.toModel(restauranteSalvo);
            System.out.println("Nome da Cozinha associada ao Restaurante: " + restauranteModel.getCozinha().getNome());

            return restauranteModel;
        } catch (final CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping(value = "/{id}")
    public RestauranteModel atualizar(
            @PathVariable(value = "id") final Long id,
            @RequestBody @Valid final Restaurante restaurante
    ) {
        final Restaurante restauranteAtual = this.service.buscarOuFalhar(id);

        BeanUtils.copyProperties(
                restaurante,
                restauranteAtual,
                "id", "formasPagamento", "endereco","dataCadastro"
        );

        try {
            final Restaurante restauranteSalvo = this.service.salvar(restauranteAtual);

            final RestauranteModel restauranteModel = this.toModel(restauranteSalvo);

            return restauranteModel;
        } catch (final CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PatchMapping(value = "/{id}")
    public RestauranteModel atualizarParcialmente(
            @PathVariable("id") final Long id,
            @RequestBody final Map<String, Object> campos,
            final HttpServletRequest request
    ) {
        final Restaurante restauranteAtual = this.service.buscarOuFalhar(id);

        merge(campos, restauranteAtual, request);

        validate(restauranteAtual, "restaurante");

        final RestauranteModel atualizar = this.atualizar(id, restauranteAtual);

        return atualizar;
    }

    private void validate(final Restaurante restaurante, final String objectName) {
        final BeanPropertyBindingResult bindingResult =
                new BeanPropertyBindingResult(restaurante, objectName);

        this.validator.validate(restaurante, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ValidacaoException(bindingResult);
        }
    }

    private void merge(
            final Map<String, Object> dadosOrigem,
            final Restaurante restauranteDestino,
            final HttpServletRequest request
            ) {
        final ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(request);

        try {
            final ObjectMapper objectMapper = new ObjectMapper();

            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            final Restaurante restauranteOrigem = objectMapper.convertValue(
                    dadosOrigem,
                    Restaurante.class
            );

            dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
                Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
                field.setAccessible(true);

                final Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

                ReflectionUtils.setField(field, restauranteDestino, novoValor);
            });
        } catch (final IllegalArgumentException ex) {
            final Throwable rootCause = ExceptionUtils.getRootCause(ex);

            throw new HttpMessageNotReadableException(
                    ex.getMessage(),
                    rootCause,
                    servletServerHttpRequest
            );
        }
    }

    private RestauranteModel toModel(final Restaurante restaurante) {
        final CozinhaModel cozinhaModel = new CozinhaModel();
        cozinhaModel.setId(restaurante.getCozinha().getId());
        cozinhaModel.setNome(restaurante.getCozinha().getNome());

        final RestauranteModel restauranteModel = new RestauranteModel();
        restauranteModel.setId(restaurante.getId());
        restauranteModel.setNome(restaurante.getNome());
        restauranteModel.setTaxaFrete(restaurante.getTaxaFrete());
        restauranteModel.setCozinha(cozinhaModel);
        return restauranteModel;
    }

    private List<RestauranteModel> toCollectionModel(final List<Restaurante> restaurantes) {
        final List<RestauranteModel> restaurantesModels = new ArrayList<>();

        for (final Restaurante restaurante : restaurantes) {
            final RestauranteModel restauranteModel = this.toModel(restaurante);

            restaurantesModels.add(restauranteModel);
        }

        return restaurantesModels;
    }

}