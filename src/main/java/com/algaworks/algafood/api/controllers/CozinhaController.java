package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository repository;

    @Autowired
    private CadastroCozinhaService service;

    @GetMapping
    public ResponseEntity<List<Cozinha>> listar() {
        List<Cozinha> cozinhas = this.repository.findAll();

        ResponseEntity<List<Cozinha>> cozinhasResponse = ResponseEntity
                .status(HttpStatus.OK)
                .body(cozinhas);

        return cozinhasResponse;
    }

    @GetMapping(value = "/{id}")
    public Cozinha buscar(@PathVariable(value = "id") Long id) {
        final Cozinha cozinha = this.service.buscarOuFalhar(id);

        return cozinha;
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
    public Cozinha atualizar(
            @PathVariable("id") Long id,
            @RequestBody Cozinha cozinha
    ) {
        final Cozinha cozinhaAtual = this.service.buscarOuFalhar(id);

        BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

        final Cozinha cozinhaSalva = this.service.salvar(cozinhaAtual);

        return cozinhaSalva;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void remover(@PathVariable("id") Long id) {
        this.service.excluir(id);
    }

}
