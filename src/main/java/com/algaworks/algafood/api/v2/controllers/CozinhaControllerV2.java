package com.algaworks.algafood.api.v2.controllers;

import com.algaworks.algafood.api.v2.assembler.CozinhaInputDTODisassemblerV2;
import com.algaworks.algafood.api.v2.assembler.CozinhaOutputDTOAssemblerV2;
import com.algaworks.algafood.api.v2.model.CozinhaOutputDTOV2;
import com.algaworks.algafood.api.v2.model.in.CozinhaInputDTOV2;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v2/cozinhas")
public class CozinhaControllerV2 {

    private final CozinhaRepository repository;
    private final CadastroCozinhaService service;
    private final CozinhaInputDTODisassemblerV2 inputDTODisassembler;
    private final CozinhaOutputDTOAssemblerV2 outputDTOAssembler;
    private final PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @Autowired
    public CozinhaControllerV2(final CozinhaRepository repository,
                               final CadastroCozinhaService service,
                               final CozinhaInputDTODisassemblerV2 inputDTODisassembler,
                               final CozinhaOutputDTOAssemblerV2 outputDTOAssembler,
                               final PagedResourcesAssembler<Cozinha> pagedResourcesAssembler) {
        this.repository = repository;
        this.service = service;
        this.inputDTODisassembler = inputDTODisassembler;
        this.outputDTOAssembler = outputDTOAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedModel<CozinhaOutputDTOV2>> listar(final Pageable pageable) {
        final Page<Cozinha> cozinhas = this.repository.findAll(pageable);

        final PagedModel<CozinhaOutputDTOV2> cozinhasModelPage = this.pagedResourcesAssembler
                .toModel(cozinhas, this.outputDTOAssembler);

        final ResponseEntity<PagedModel<CozinhaOutputDTOV2>> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(cozinhasModelPage);

        return response;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CozinhaOutputDTOV2> buscar(@PathVariable(value = "id") final Long id) {
        final Cozinha cozinha = this.service.buscarOuFalhar(id);

        final CozinhaOutputDTOV2 cozinhaOutputDTO = this.outputDTOAssembler.toModel(cozinha);

        final ResponseEntity<CozinhaOutputDTOV2> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(cozinhaOutputDTO);

        return response;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CozinhaOutputDTOV2> adicionar(
            @RequestBody @Valid final CozinhaInputDTOV2 cozinhaInputDTO
    ) {
        final Cozinha cozinha = this.inputDTODisassembler
                .toDomainObject(cozinhaInputDTO);

        final Cozinha cozinhaSalva = this.service.salvar(cozinha);

        final CozinhaOutputDTOV2 cozinhaOutputDTO = this.outputDTOAssembler
                .toModel(cozinhaSalva);

        final ResponseEntity<CozinhaOutputDTOV2> response = ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cozinhaOutputDTO);

        return response;
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CozinhaOutputDTOV2> atualizar(
            @PathVariable("id") Long id,
            @RequestBody @Valid final CozinhaInputDTOV2 cozinhaInputDTO
    ) {
        final Cozinha cozinhaAtual = this.service.buscarOuFalhar(id);

        this.inputDTODisassembler.copyToDomainObject(cozinhaInputDTO, cozinhaAtual);

        final Cozinha cozinhaSalva = this.service.salvar(cozinhaAtual);

        final CozinhaOutputDTOV2 cozinhaOutputDTO = this.outputDTOAssembler
                .toModel(cozinhaSalva);

        final ResponseEntity<CozinhaOutputDTOV2> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(cozinhaOutputDTO);

        return response;
    }

    @DeleteMapping(value = "/{id}", produces = {})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> remover(@PathVariable("id") Long id) {
        this.service.excluir(id);

        final ResponseEntity<Void> response = ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

        return response;
    }

}
