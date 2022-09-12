package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {

    @Autowired
    private CadastroEstadoService service;

    @GetMapping
    public ResponseEntity<List<Estado>> listar() {
        List<Estado> estados = this.service.listar();

        ResponseEntity<List<Estado>> estadosReponse = ResponseEntity
                .status(HttpStatus.OK)
                .body(estados);

        return estadosReponse;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> buscar(@PathVariable(value = "id") Long id) {
        try {
            Estado estado = this.service.buscar(id);

            ResponseEntity<Estado> estadoResponse = ResponseEntity
                    .status(HttpStatus.OK)
                    .body(estado);

            return estadoResponse;
        } catch (EntidadeNaoEncontradaException ex) {
            ResponseEntity<String> estadoResponse = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());

            return estadoResponse;
        }
    }

    @PostMapping
    public ResponseEntity<Estado> adicionar(@RequestBody Estado estado) {
        Estado estadoSalvo = this.service.salvar(estado);

        ResponseEntity<Estado> estadoResponse = ResponseEntity
                .status(HttpStatus.CREATED)
                .body(estadoSalvo);

        return estadoResponse;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> atualizar(
            @PathVariable(value = "id") Long id,
            @RequestBody Estado estado
    ) {
        try {
            Estado estadoAtual = this.service.buscar(id);

            BeanUtils.copyProperties(estado, estadoAtual, "id");

            Estado estadoSalvo = this.service.salvar(estadoAtual);

            ResponseEntity<Estado> estadoResponse = ResponseEntity
                    .status(HttpStatus.OK)
                    .body(estadoSalvo);

            return estadoResponse;
        } catch (EntidadeNaoEncontradaException ex) {
            ResponseEntity<String> estadoResponse = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());

            return estadoResponse;
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> remover(@PathVariable(value = "id") Long id) {
        try {
            this.service.excluir(id);

            ResponseEntity<Estado> estadoResponse = ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();

            return estadoResponse;
        } catch (EntidadeNaoEncontradaException ex) {
            ResponseEntity<String> estadoResponse = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());

            return estadoResponse;
        } catch (EntidadeEmUsoException ex) {
            ResponseEntity<String> estadoResponse = ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(ex.getMessage());

            return estadoResponse;
        }
    }
}
