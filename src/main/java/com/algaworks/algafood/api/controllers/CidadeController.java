package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        final Cidade cidadeSalva = this.service.salvar(cidade);

        return cidadeSalva;
    }

    @PutMapping(value = "/{id}")
    public Cidade atualizar(
            @PathVariable(value = "id") final Long id,
            @RequestBody final Cidade cidade
    ) {
        final Cidade cidadeAtual = this.service.buscarOuFalhar(id);

        BeanUtils.copyProperties(cidade, cidadeAtual, "id");

        final Cidade cidadeSalva = this.service.salvar(cidadeAtual);

        return cidadeSalva;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void remover(@PathVariable(value = "id") Long id) {
        this.service.excluir(id);
    }

}
