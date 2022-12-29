package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        final List<Cozinha> cozinhas = this.repository.findAll();

        return cozinhas;
    }

    @GetMapping(value = "/{id}")
    public Cozinha buscar(@PathVariable(value = "id") final Long id) {
        final Cozinha cozinha = this.service.buscarOuFalhar(id);

        return cozinha;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody final Cozinha cozinha) {
        final Cozinha cozinhaSalva = this.service.salvar(cozinha);

        return cozinhaSalva;
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
