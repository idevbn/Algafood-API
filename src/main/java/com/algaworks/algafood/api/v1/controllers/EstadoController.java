package com.algaworks.algafood.api.v1.controllers;

import com.algaworks.algafood.api.v1.assembler.EstadoInputDTODisassembler;
import com.algaworks.algafood.api.v1.assembler.EstadoOutputDTOAssembler;
import com.algaworks.algafood.api.v1.model.in.EstadoInputDTO;
import com.algaworks.algafood.api.v1.model.out.EstadoOutputDTO;
import com.algaworks.algafood.api.v1.openapi.controllers.EstadoControllerOpenApi;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenApi {

    private final EstadoRepository repository;
    private final CadastroEstadoService service;
    private final EstadoInputDTODisassembler inputDTODisassembler;
    private final EstadoOutputDTOAssembler outputDTOAssembler;

    @Autowired
    public EstadoController(final EstadoRepository repository,
                            final CadastroEstadoService service,
                            final EstadoInputDTODisassembler inputDTODisassembler,
                            final EstadoOutputDTOAssembler outputDTOAssembler) {
        this.repository = repository;
        this.service = service;
        this.inputDTODisassembler = inputDTODisassembler;
        this.outputDTOAssembler = outputDTOAssembler;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionModel<EstadoOutputDTO>> listar() {
        final List<Estado> estados = this.repository.findAll();

        final CollectionModel<EstadoOutputDTO> estadosOutputDTOS = this.outputDTOAssembler
                .toCollectionModel(estados);

        final ResponseEntity<CollectionModel<EstadoOutputDTO>> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(estadosOutputDTOS);

        return response;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstadoOutputDTO> buscar(@PathVariable(value = "id") final Long id) {

        final Estado estadoEncontrado = this.service.buscarOuFalhar(id);

        final EstadoOutputDTO estadoOutputDTO = this.outputDTOAssembler
                .toModel(estadoEncontrado);

        final ResponseEntity<EstadoOutputDTO> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(estadoOutputDTO);

        return response;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstadoOutputDTO> adicionar(
            @RequestBody @Valid final EstadoInputDTO estadoInputDTO
    ) {
        final Estado estado = this.inputDTODisassembler
                .toDomainObject(estadoInputDTO);

        final Estado estadoSalvo = this.service.salvar(estado);

        final EstadoOutputDTO estadoOutputDTO = this.outputDTOAssembler
                .toModel(estadoSalvo);

        final ResponseEntity<EstadoOutputDTO> response = ResponseEntity
                .status(HttpStatus.CREATED)
                .body(estadoOutputDTO);

        return response;
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstadoOutputDTO> atualizar(
            @PathVariable(value = "id") final Long id,
            @RequestBody @Valid final EstadoInputDTO estadoInputDTO
    ) {
        final Estado estadoAtual = this.service.buscarOuFalhar(id);

        this.inputDTODisassembler.copyToDomainObject(estadoInputDTO, estadoAtual);

        final Estado estadoSalvo = this.service.salvar(estadoAtual);

        final EstadoOutputDTO estadoOutputDTO = this.outputDTOAssembler
                .toModel(estadoSalvo);

        final ResponseEntity<EstadoOutputDTO> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(estadoOutputDTO);

        return response;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> remover(@PathVariable(value = "id") final Long id) {
        this.service.excluir(id);

        ResponseEntity<Void> response = ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

        return response;
    }

}
