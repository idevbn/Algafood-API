package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.api.model.in.CozinhaInputDTO;
import com.algaworks.algafood.api.model.in.RestauranteInputDTO;
import com.algaworks.algafood.api.model.out.CozinhaOutputDTO;
import com.algaworks.algafood.api.model.out.RestauranteOutputDTO;
import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cozinha;
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
    public List<RestauranteOutputDTO> listar() {
        final List<Restaurante> restaurantes = this.repository.findAll();

        final List<RestauranteOutputDTO> restauranteOutputDTO = this.toCollectionModel(restaurantes);

        return restauranteOutputDTO;
    }

    @GetMapping(value = "/{id}")
    public RestauranteOutputDTO buscar(@PathVariable(value = "id") final Long id) {
        final Restaurante restauranteEncontrado = this.service.buscarOuFalhar(id);

        final RestauranteOutputDTO restauranteOutputDTO = this.toModel(restauranteEncontrado);

        return restauranteOutputDTO;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public RestauranteOutputDTO adicionar(
            @RequestBody @Valid final RestauranteInputDTO restauranteInputDTO
    ) {
        try {
            final Restaurante restaurante = this.toDomainObject(restauranteInputDTO);

            final Restaurante restauranteSalvo = this.service.salvar(restaurante);

            final RestauranteOutputDTO restauranteOutputDTO = this.toModel(restauranteSalvo);

            return restauranteOutputDTO;
        } catch (final CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping(value = "/{id}")
    public RestauranteOutputDTO atualizar(
            @PathVariable(value = "id") final Long id,
            @RequestBody @Valid final RestauranteInputDTO restauranteInputDTO
    ) {
        final Restaurante restaurante = this.toDomainObject(restauranteInputDTO);

        final Restaurante restauranteAtual = this.service.buscarOuFalhar(id);

        BeanUtils.copyProperties(
                restaurante,
                restauranteAtual,
                "id", "formasPagamento", "endereco","dataCadastro"
        );

        try {
            final Restaurante restauranteSalvo = this.service.salvar(restauranteAtual);

            final RestauranteOutputDTO restauranteOutputDTO = this.toModel(restauranteSalvo);

            return restauranteOutputDTO;
        } catch (final CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PatchMapping(value = "/{id}")
    public RestauranteOutputDTO atualizarParcialmente(
            @PathVariable("id") final Long id,
            @RequestBody final Map<String, Object> campos,
            final HttpServletRequest request
    ) {
        final Restaurante restauranteAtual = this.service.buscarOuFalhar(id);

        merge(campos, restauranteAtual, request);

        validate(restauranteAtual, "restaurante");

        final RestauranteOutputDTO atualizar = this
                .atualizar(id, this.toInputObject(restauranteAtual));

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

    private RestauranteOutputDTO toModel(final Restaurante restaurante) {
        final CozinhaOutputDTO cozinhaDTO = new CozinhaOutputDTO();
        cozinhaDTO.setId(restaurante.getCozinha().getId());
        cozinhaDTO.setNome(restaurante.getCozinha().getNome());

        final RestauranteOutputDTO restauranteOutputDTO = new RestauranteOutputDTO();
        restauranteOutputDTO.setId(restaurante.getId());
        restauranteOutputDTO.setNome(restaurante.getNome());
        restauranteOutputDTO.setTaxaFrete(restaurante.getTaxaFrete());
        restauranteOutputDTO.setCozinha(cozinhaDTO);
        return restauranteOutputDTO;
    }

    private List<RestauranteOutputDTO> toCollectionModel(final List<Restaurante> restaurantes) {
        final List<RestauranteOutputDTO> restaurantesOutputDTO = new ArrayList<>();

        for (final Restaurante restaurante : restaurantes) {
            final RestauranteOutputDTO restauranteOutputDTO = this.toModel(restaurante);

            restaurantesOutputDTO.add(restauranteOutputDTO);
        }

        return restaurantesOutputDTO;
    }

    private Restaurante toDomainObject(final RestauranteInputDTO restauranteInputDTO) {
        final Restaurante restaurante = new Restaurante();

        restaurante.setNome(restauranteInputDTO.getNome());
        restaurante.setTaxaFrete(restauranteInputDTO.getTaxaFrete());

        final Cozinha cozinha = new Cozinha();
        cozinha.setId(restauranteInputDTO.getCozinha().getId());

        restaurante.setCozinha(cozinha);

        return restaurante;
    }

    private RestauranteInputDTO toInputObject(final Restaurante restaurante) {
        final RestauranteInputDTO restauranteInputDTO = new RestauranteInputDTO();
        restauranteInputDTO.setNome(restaurante.getNome());
        restauranteInputDTO.setTaxaFrete(restaurante.getTaxaFrete());

        final CozinhaInputDTO cozinhaInputDTO = new CozinhaInputDTO();
        cozinhaInputDTO.setId(restaurante.getCozinha().getId());

        restauranteInputDTO.setCozinha(cozinhaInputDTO);

        return restauranteInputDTO;
    }

}