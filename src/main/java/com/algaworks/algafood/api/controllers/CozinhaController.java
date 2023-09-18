package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.api.assembler.CozinhaInputDTODisassembler;
import com.algaworks.algafood.api.assembler.CozinhaOutputDTOAssembler;
import com.algaworks.algafood.api.model.in.CozinhaInputDTO;
import com.algaworks.algafood.api.model.out.CozinhaOutputDTO;
import com.algaworks.algafood.api.openapi.controllers.CozinhaControllerOpenApi;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/cozinhas")
public class CozinhaController implements CozinhaControllerOpenApi {

    private final CozinhaRepository repository;
    private final CadastroCozinhaService service;
    private final CozinhaInputDTODisassembler inputDTODisassembler;
    private final CozinhaOutputDTOAssembler outputDTOAssembler;
    private final PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @Autowired
    public CozinhaController(final CozinhaRepository repository,
                             final CadastroCozinhaService service,
                             final CozinhaInputDTODisassembler inputDTODisassembler,
                             final CozinhaOutputDTOAssembler outputDTOAssembler,
                             final PagedResourcesAssembler<Cozinha> pagedResourcesAssembler) {
        this.repository = repository;
        this.service = service;
        this.inputDTODisassembler = inputDTODisassembler;
        this.outputDTOAssembler = outputDTOAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedModel<CozinhaOutputDTO>> listar(final Pageable pageable) {
        final Page<Cozinha> cozinhas = this.repository.findAll(pageable);

        final PagedModel<CozinhaOutputDTO> cozinhasModelPage = this.pagedResourcesAssembler
                .toModel(cozinhas, this.outputDTOAssembler);

        final ResponseEntity<PagedModel<CozinhaOutputDTO>> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(cozinhasModelPage);

        return response;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CozinhaOutputDTO> buscar(@PathVariable(value = "id") final Long id) {
        final Cozinha cozinha = this.service.buscarOuFalhar(id);

        final CozinhaOutputDTO cozinhaOutputDTO = this.outputDTOAssembler.toModel(cozinha);

        final ResponseEntity<CozinhaOutputDTO> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(cozinhaOutputDTO);

        return response;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
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

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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
