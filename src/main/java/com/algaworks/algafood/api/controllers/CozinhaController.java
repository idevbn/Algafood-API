package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.api.assembler.CozinhaInputDTODisassembler;
import com.algaworks.algafood.api.assembler.CozinhaOutputDTOAssembler;
import com.algaworks.algafood.api.model.in.CozinhaInputDTO;
import com.algaworks.algafood.api.model.out.CozinhaOutputDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

    private final CozinhaRepository repository;
    private final CadastroCozinhaService service;
    private final CozinhaInputDTODisassembler inputDTODisassembler;
    private final CozinhaOutputDTOAssembler outputDTOAssembler;

    @Autowired
    public CozinhaController(final CozinhaRepository repository,
                             final CadastroCozinhaService service,
                             final CozinhaInputDTODisassembler inputDTODisassembler,
                             final CozinhaOutputDTOAssembler outputDTOAssembler) {
        this.repository = repository;
        this.service = service;
        this.inputDTODisassembler = inputDTODisassembler;
        this.outputDTOAssembler = outputDTOAssembler;
    }

    @GetMapping
    public ResponseEntity<Page<CozinhaOutputDTO>> listar(final Pageable pageable) {
        final Page<Cozinha> cozinhas = this.repository.findAll(pageable);

        final List<CozinhaOutputDTO> cozinhasOutputDTOS = this.outputDTOAssembler
                .toCollectionModel(cozinhas.getContent());

        final Page<CozinhaOutputDTO> cozinhasPageResponse =
                new PageImpl<>(cozinhasOutputDTOS, pageable, cozinhas.getTotalElements());

        final ResponseEntity<Page<CozinhaOutputDTO>> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(cozinhasPageResponse);

        return response;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CozinhaOutputDTO> buscar(@PathVariable(value = "id") final Long id) {
        final Cozinha cozinha = this.service.buscarOuFalhar(id);

        final CozinhaOutputDTO cozinhaOutputDTO = this.outputDTOAssembler.toModel(cozinha);

        final ResponseEntity<CozinhaOutputDTO> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(cozinhaOutputDTO);

        return response;
    }

    @PostMapping
    public ResponseEntity<CozinhaOutputDTO> adicionar(
            @RequestBody @Valid final CozinhaInputDTO cozinhaInputDTO
    ) {
        final Cozinha cozinha = this.inputDTODisassembler
                .toDomainObject(cozinhaInputDTO);

        final Cozinha cozinhaSalva = this.service.salvar(cozinha);

        final CozinhaOutputDTO cozinhaOutputDTO = this.outputDTOAssembler
                .toModel(cozinhaSalva);

        final ResponseEntity<CozinhaOutputDTO> response = ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cozinhaOutputDTO);

        return response;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CozinhaOutputDTO> atualizar(
            @PathVariable("id") Long id,
            @RequestBody @Valid final CozinhaInputDTO cozinhaInputDTO
    ) {
        final Cozinha cozinhaAtual = this.service.buscarOuFalhar(id);

        this.inputDTODisassembler.copyToDomainObject(cozinhaInputDTO, cozinhaAtual);

        final Cozinha cozinhaSalva = this.service.salvar(cozinhaAtual);

        final CozinhaOutputDTO cozinhaOutputDTO = this.outputDTOAssembler
                .toModel(cozinhaSalva);

        final ResponseEntity<CozinhaOutputDTO> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(cozinhaOutputDTO);

        return response;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> remover(@PathVariable("id") Long id) {
        this.service.excluir(id);

        final ResponseEntity<Void> response = ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

        return response;
    }

}
