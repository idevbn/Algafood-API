package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.api.assembler.RestauranteInputDTODisassembler;
import com.algaworks.algafood.api.assembler.RestauranteOutputDTOAssembler;
import com.algaworks.algafood.api.model.in.CozinhaIdInputDTO;
import com.algaworks.algafood.api.model.in.RestauranteInputDTO;
import com.algaworks.algafood.api.model.out.RestauranteOutputDTO;
import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

    private final RestauranteRepository repository;

    private final CadastroRestauranteService service;

    private final SmartValidator validator;

    private final RestauranteOutputDTOAssembler assembler;

    private final RestauranteInputDTODisassembler disassembler;

    @Autowired
    public RestauranteController(final RestauranteRepository repository,
                                 final CadastroRestauranteService service,
                                 final SmartValidator validator,
                                 final RestauranteOutputDTOAssembler assembler,
                                 final RestauranteInputDTODisassembler disassembler) {
        this.repository = repository;
        this.service = service;
        this.validator = validator;
        this.assembler = assembler;
        this.disassembler = disassembler;
    }
    
    @GetMapping
    public ResponseEntity<List<RestauranteOutputDTO>> listar() {
        final List<Restaurante> restaurantes = this.repository.findAll();

        final List<RestauranteOutputDTO> restauranteOutputDTO = this.assembler
                .toCollectionModel(restaurantes);

        final ResponseEntity<List<RestauranteOutputDTO>> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(restauranteOutputDTO);

        return response;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RestauranteOutputDTO> buscar(@PathVariable(value = "id") final Long id) {
        final Restaurante restauranteEncontrado = this.service.buscarOuFalhar(id);

        final RestauranteOutputDTO restauranteOutputDTO = this.assembler
                .toModel(restauranteEncontrado);

        final ResponseEntity<RestauranteOutputDTO> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(restauranteOutputDTO);

        return response;
    }

    @PostMapping
    public ResponseEntity<RestauranteOutputDTO> adicionar(
            @RequestBody @Valid final RestauranteInputDTO restauranteInputDTO
    ) {
        try {
            final Restaurante restaurante = this.disassembler
                    .toDomainObject(restauranteInputDTO);

            final Restaurante restauranteSalvo = this.service.salvar(restaurante);

            final RestauranteOutputDTO restauranteOutputDTO = this.assembler
                    .toModel(restauranteSalvo);

            final ResponseEntity<RestauranteOutputDTO> response = ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(restauranteOutputDTO);

            return response;
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<RestauranteOutputDTO> atualizar(
            @PathVariable(value = "id") final Long id,
            @RequestBody @Valid final RestauranteInputDTO restauranteInputDTO
    ) {

        final Restaurante restauranteAtual = this.service.buscarOuFalhar(id);

        this.disassembler.copyToDomainObject(restauranteInputDTO, restauranteAtual);

        try {
            final Restaurante restauranteSalvo = this.service.salvar(restauranteAtual);

            final RestauranteOutputDTO restauranteOutputDTO = this.assembler
                    .toModel(restauranteSalvo);

            final ResponseEntity<RestauranteOutputDTO> response = ResponseEntity
                    .status(HttpStatus.OK)
                    .body(restauranteOutputDTO);

            return response;
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<RestauranteOutputDTO> atualizarParcialmente(
            @PathVariable("id") final Long id,
            @RequestBody final Map<String, Object> campos,
            final HttpServletRequest request
    ) {
        final Restaurante restauranteAtual = this.service.buscarOuFalhar(id);

        merge(campos, restauranteAtual, request);

        validate(restauranteAtual, "restaurante");

        final ResponseEntity<RestauranteOutputDTO> atualizar = this
                .atualizar(id, this.toInputObject(restauranteAtual));

        return atualizar;
    }

    /**
     * PUT /restaurantes/{id}/ativo -> ativa um
     * {@link Restaurante} com base no valor do
     * @param id
     *
     * Uma requisição do tipo PUT não gera efeitos
     * colaterais caso seja realizada várias vezes
     * em sequência.
     * O POST não é IDEMPOTENTE.
     */
    @PutMapping(value = "/{id}/ativo")
    public ResponseEntity<Void> ativar(@PathVariable("id") final Long id) {
        this.service.ativar(id);

        final ResponseEntity<Void> response = ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

        return response;
    }

    /**
     * DELETE /restaurantes/{id}/ativo -> inativa
     * um {@link Restaurante} com base no valor do
     * @param id
     */
    @DeleteMapping(value = "/{id}/ativo")
    public ResponseEntity<Void> inativar(@PathVariable("id") final Long id) {
        this.service.inativar(id);

        final ResponseEntity<Void> response = ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

        return response;
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

    private RestauranteInputDTO toInputObject(final Restaurante restaurante) {
        final RestauranteInputDTO restauranteInputDTO = new RestauranteInputDTO();
        restauranteInputDTO.setNome(restaurante.getNome());
        restauranteInputDTO.setTaxaFrete(restaurante.getTaxaFrete());

        final CozinhaIdInputDTO cozinhaInputDTO = new CozinhaIdInputDTO();
        cozinhaInputDTO.setId(restaurante.getCozinha().getId());

        restauranteInputDTO.setCozinha(cozinhaInputDTO);

        return restauranteInputDTO;
    }

}