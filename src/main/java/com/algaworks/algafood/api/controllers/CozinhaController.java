package com.algaworks.algafood.api.controllers;

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
    public ResponseEntity<Cozinha> buscar(@PathVariable(value = "id") Long id) {
        Optional<Cozinha> cozinha = this.repository.findById(id);

        if (cozinha.isPresent()) {
            ResponseEntity<Cozinha> cozinhaResponse = ResponseEntity
                    .status(HttpStatus.OK)
                    .body(cozinha.get());

            return cozinhaResponse;
        }
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
        Optional<Cozinha> cozinhaAtual = this.repository.findById(id);

        if (cozinhaAtual.isPresent()) {
            BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id");

            Cozinha cozinhaSalva = this.service.salvar(cozinhaAtual.get());

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

//    @DeleteMapping(value = "/{id}")
//    public ResponseEntity<?> remover(@PathVariable("id") Long id) {
//        try {
//            this.service.excluir(id);
//
//            ResponseEntity<Cozinha> cozinhaResponse = ResponseEntity
//                    .status(HttpStatus.NO_CONTENT)
//                    .build();
//
//            return cozinhaResponse;
//        } catch (EntidadeNaoEncontradaException ex) {
//            ResponseEntity<?> cozinhaResponse = ResponseEntity
//                    .status(HttpStatus.NOT_FOUND)
//                    .body(ex.getMessage());
//
//            return cozinhaResponse;
//        } catch (EntidadeEmUsoException ex) {
//            ResponseEntity<?> cozinhaResponse = ResponseEntity
//                    .status(HttpStatus.CONFLICT)
//                    .body(ex.getMessage());
//
//            return cozinhaResponse;
//        }
//    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void remover(@PathVariable("id") Long id) {
        this.service.excluir(id);
    }

}
