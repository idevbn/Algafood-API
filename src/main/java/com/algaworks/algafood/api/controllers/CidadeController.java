package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

    @Autowired
    private CadastroCidadeService service;

    @GetMapping
    public ResponseEntity<List<Cidade>> listar() {
        List<Cidade> cidades = this.service.listar();

        ResponseEntity<List<Cidade>> cidadesResponse = ResponseEntity
                .status(HttpStatus.OK)
                .body(cidades);

        return cidadesResponse;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> buscar(@PathVariable(value = "id") Long id) {
        try {
            Cidade cidade = this.service.buscar(id);

            ResponseEntity<Cidade> cidadeResponse = ResponseEntity
                    .status(HttpStatus.OK)
                    .body(cidade);

            return cidadeResponse;
        } catch (EntidadeNaoEncontradaException ex) {
            ResponseEntity<String> cidadeResponse = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());

            return cidadeResponse;
        }
    }

    @PostMapping
    public ResponseEntity<Cidade> adicionar(@RequestBody Cidade cidade) {
        Cidade cidadeSalva = this.service.salvar(cidade);

        ResponseEntity<Cidade> cidadeResponse = ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cidadeSalva);

        return cidadeResponse;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> atualizar(
        @PathVariable(value = "id") Long id,
        @RequestBody Cidade cidade
    ) {
        try {
            Cidade cidadeAtual = this.service.buscar(id);

            BeanUtils.copyProperties(cidade, cidadeAtual, "id");

            Cidade cidadeSalva = this.service.salvar(cidadeAtual);

            ResponseEntity<Cidade> cidadeResponse = ResponseEntity
                    .status(HttpStatus.OK)
                    .body(cidadeSalva);

            return cidadeResponse;
        } catch (EntidadeNaoEncontradaException ex) {
            ResponseEntity<String> cidadeResponse = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());

            return cidadeResponse;
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> remover(@PathVariable(value = "id") Long id) {
        try {
            this.service.excluir(id);

            ResponseEntity<Cidade> cidadeResponse = ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();

            return cidadeResponse;
        } catch (EntidadeNaoEncontradaException ex) {
            ResponseEntity<String> cidadeResponse = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());

            return cidadeResponse;
        }
    }
}
