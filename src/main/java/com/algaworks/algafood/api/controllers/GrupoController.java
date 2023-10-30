package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.api.assembler.GrupoInputDTODisassembler;
import com.algaworks.algafood.api.assembler.GrupoOutputDTOAssembler;
import com.algaworks.algafood.api.model.in.GrupoInputDTO;
import com.algaworks.algafood.api.model.out.GrupoOutputDTO;
import com.algaworks.algafood.api.openapi.controllers.GrupoControllerOpenApi;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {

    private final GrupoRepository repository;
    private final CadastroGrupoService service;
    private final GrupoInputDTODisassembler inputDTODisassembler;
    private final GrupoOutputDTOAssembler outputDTOAssembler;

    public GrupoController(final GrupoRepository repository,
                           final CadastroGrupoService service,
                           final GrupoInputDTODisassembler inputDTODisassembler,
                           final GrupoOutputDTOAssembler outputDTOAssembler) {
        this.repository = repository;
        this.service = service;
        this.inputDTODisassembler = inputDTODisassembler;
        this.outputDTOAssembler = outputDTOAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<GrupoOutputDTO>> listar() {
        final List<Grupo> grupos = this.repository.findAll();

        final CollectionModel<GrupoOutputDTO> gruposOutputDTOS = this.outputDTOAssembler
                .toCollectionModel(grupos);

        final ResponseEntity<CollectionModel<GrupoOutputDTO>> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(gruposOutputDTOS);

        return response;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GrupoOutputDTO> buscar(@PathVariable("id") final Long id) {
        final Grupo grupo = this.service.buscarOuFalhar(id);

        final GrupoOutputDTO grupoOutputDTO = this.outputDTOAssembler
                .toModel(grupo);

        final ResponseEntity<GrupoOutputDTO> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(grupoOutputDTO);

        return response;
    }

    @PostMapping
    public ResponseEntity<GrupoOutputDTO> adicionar(
            @RequestBody @Valid final GrupoInputDTO grupoInputDTO
    ) {
        final Grupo grupo = this.inputDTODisassembler.toDomainObject(grupoInputDTO);

        final Grupo grupoSalvo = this.service.salvar(grupo);

        final GrupoOutputDTO grupoOutputDTO = this.outputDTOAssembler
                .toModel(grupoSalvo);

        final ResponseEntity<GrupoOutputDTO> response = ResponseEntity
                .status(HttpStatus.CREATED)
                .body(grupoOutputDTO);

        return response;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<GrupoOutputDTO> atualizar(
            @PathVariable("id") final Long id,
            @RequestBody @Valid final GrupoInputDTO grupoInputDTO
    ) {
        final Grupo grupoAtual = this.service.buscarOuFalhar(id);

        this.inputDTODisassembler.copyToDomainObject(grupoInputDTO, grupoAtual);

        final Grupo grupoSalvo = this.service.salvar(grupoAtual);

        final GrupoOutputDTO grupoOutputDTO = this.outputDTOAssembler
                .toModel(grupoSalvo);

        final ResponseEntity<GrupoOutputDTO> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(grupoOutputDTO);

        return response;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> excluir(@PathVariable("id") final Long id) {
        this.service.excluir(id);

        final ResponseEntity<Void> response = ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

        return response;
    }

}
