package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository repository;

    @Autowired
    private CadastroCozinhaService service;

    @GetMapping
    public List<Cozinha> listar() {
        List<Cozinha> cozinhas = this.repository.listar();

        return cozinhas;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Cozinha> buscar(@PathVariable(value = "id") Long id) {
        Cozinha cozinha = this.repository.buscar(id);

        if (cozinha != null) {
            // Outra forma (shortcut): ResponseEntity.ok(cozinha);
            ResponseEntity<Cozinha> cozinhaResponse = ResponseEntity.status(HttpStatus.OK).body(cozinha);

            return cozinhaResponse;
        }
        // Outra forma (shortcut): ResponseEntity.notFound().build();
        ResponseEntity<Cozinha> cozinhaResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return cozinhaResponse;
    }

    @PostMapping
    public ResponseEntity<Cozinha> adicionar(@RequestBody Cozinha cozinha) {
        Cozinha cozinhaSalva = this.service.salvar(cozinha);

        ResponseEntity<Cozinha> cozinhaResponse = ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cozinhaSalva);

        return cozinhaResponse;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Cozinha> atualizar(
            @PathVariable("id") Long id,
            @RequestBody Cozinha cozinha
    ) {
        Cozinha cozinhaAtual = this.repository.buscar(id);

        if (cozinhaAtual != null) {
            BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

            Cozinha cozinhaSalva = this.repository.salvar(cozinhaAtual);

            ResponseEntity<Cozinha> cozinhaResponse = ResponseEntity
                    .status(HttpStatus.OK)
                    .body(cozinhaSalva);

            return cozinhaResponse;
        }

        ResponseEntity<Cozinha> cozinhaResponse = ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();

        return cozinhaResponse;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Cozinha> remover(@PathVariable("id") Long id) {
        try {
            Cozinha cozinhaBuscada = this.repository.buscar(id);

            if (cozinhaBuscada != null) {
                this.repository.remover(cozinhaBuscada);

                ResponseEntity<Cozinha> cozinhaResponse = ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .build();

                return cozinhaResponse;
            }

            ResponseEntity<Cozinha> cozinhaResponse = ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();

            return cozinhaResponse;
        } catch (DataIntegrityViolationException ex) {
            ResponseEntity<Cozinha> cozinhaResponse = ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .build();

            return cozinhaResponse;
        }
    }
}
