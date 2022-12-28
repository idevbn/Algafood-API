package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository repository;

    @Autowired
    private CadastroEstadoService service;

    @GetMapping
    public List<Estado> listar() {
        final List<Estado> estados = this.repository.findAll();

        return estados;
    }

    @GetMapping(value = "/{id}")
    public Estado buscar(@PathVariable(value = "id") final Long id) {

        final Estado estadoEncontrado = this.service.buscarOuFalhar(id);

        return estadoEncontrado;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Estado adicionar(@RequestBody final Estado estado) {
        Estado estadoSalvo = this.service.salvar(estado);

        return estadoSalvo;
    }

    @PutMapping(value = "/{id}")
    public Estado atualizar(
            @PathVariable(value = "id") final Long id,
            @RequestBody Estado estado
    ) {
        final Estado estadoAtual = this.service.buscarOuFalhar(id);

        BeanUtils.copyProperties(estado, estadoAtual, "id");

        final Estado estadoSalvo = this.service.salvar(estadoAtual);

        return estadoSalvo;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void remover(@PathVariable(value = "id") final Long id) {
        this.service.excluir(id);
    }

}
