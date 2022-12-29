package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.api.exceptionhandler.ApiError;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository repository;

    @Autowired
    private CadastroCidadeService service;

    @GetMapping
    public List<Cidade> listar() {
        final List<Cidade> cidades = this.repository.findAll();

        return cidades;
    }

    @GetMapping(value = "/{id}")
    public Cidade buscar(@PathVariable(value = "id") final Long id) {
        final Cidade cidadeEncontrada = this.service.buscarOuFalhar(id);

        return cidadeEncontrada;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Cidade adicionar(@RequestBody final Cidade cidade) {
        try {
            final Cidade cidadeSalva = this.service.salvar(cidade);

            return cidadeSalva;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping(value = "/{id}")
    public Cidade atualizar(
            @PathVariable(value = "id") final Long id,
            @RequestBody final Cidade cidade
    ) {
        final Cidade cidadeAtual = this.service.buscarOuFalhar(id);

        BeanUtils.copyProperties(cidade, cidadeAtual, "id");

        try {
            final Cidade cidadeSalva = this.service.salvar(cidadeAtual);

            return cidadeSalva;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void remover(@PathVariable(value = "id") Long id) {
        this.service.excluir(id);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(
            final EntidadeNaoEncontradaException e
    ) {
        final ApiError apiError = ApiError.builder()
                .dataHora(LocalDateTime.now())
                .mensagem(e.getMessage())
                .build();

        final ResponseEntity<ApiError> response = ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(apiError);

        return response;
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(final NegocioException e) {
        final ApiError apiError = ApiError.builder()
                .dataHora(LocalDateTime.now())
                .mensagem(e.getMessage())
                .build();

        final ResponseEntity<ApiError> response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(apiError);

        return response;
    }

}
