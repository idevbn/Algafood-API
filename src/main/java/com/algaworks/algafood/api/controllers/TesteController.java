package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/teste")
public class TesteController {

    @Autowired
    private CozinhaRepository repository;

    @GetMapping(value = "/cozinhas/por-nome")
    public ResponseEntity<List<Cozinha>> cozinhasPorNome(@RequestParam String nome) {
        List<Cozinha> cozinhas = this.repository.findTodasByNome(nome);

        ResponseEntity<List<Cozinha>> cozinhasResponse = ResponseEntity
                .status(HttpStatus.OK)
                .body(cozinhas);

        return cozinhasResponse;
    }

    @GetMapping(value = "/cozinhas/unica-por-nome")
    public ResponseEntity<Optional<Cozinha>> cozinhaPorNome(@RequestParam String nome) {
        Optional<Cozinha> cozinha = this.repository.findByNome(nome);

        ResponseEntity<Optional<Cozinha>> cozinhaResponse = ResponseEntity
                .status(HttpStatus.OK)
                .body(cozinha);

        return cozinhaResponse;
    }
}
