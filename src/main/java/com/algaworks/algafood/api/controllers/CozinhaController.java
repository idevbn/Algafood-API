package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository repository;

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
        Cozinha cozinhaSalva = this.repository.salvar(cozinha);

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
}
