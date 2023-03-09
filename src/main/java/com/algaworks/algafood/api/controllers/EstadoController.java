package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.api.assembler.EstadoInputDTODisassembler;
import com.algaworks.algafood.api.assembler.EstadoOutputDTOAssembler;
import com.algaworks.algafood.api.model.in.EstadoInputDTO;
import com.algaworks.algafood.api.model.out.EstadoOutputDTO;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {

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

    @GetMapping
    public List<EstadoOutputDTO> listar() {
        final List<Estado> estados = this.repository.findAll();

        final List<EstadoOutputDTO> estadosOutputDTOS = this.outputDTOAssembler
                .toCollectionModel(estados);

        return estadosOutputDTOS;
    }

    @GetMapping(value = "/{id}")
    public EstadoOutputDTO buscar(@PathVariable(value = "id") final Long id) {

        final Estado estadoEncontrado = this.service.buscarOuFalhar(id);

        final EstadoOutputDTO estadoOutputDTO = this.outputDTOAssembler
                .toModel(estadoEncontrado);

        return estadoOutputDTO;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public EstadoOutputDTO adicionar(@RequestBody @Valid final EstadoInputDTO estadoInputDTO) {
        final Estado estado = this.inputDTODisassembler
                .toDomainObject(estadoInputDTO);

        final Estado estadoSalvo = this.service.salvar(estado);

        final EstadoOutputDTO estadoOutputDTO = this.outputDTOAssembler
                .toModel(estadoSalvo);

        return estadoOutputDTO;
    }

    @PutMapping(value = "/{id}")
    public EstadoOutputDTO atualizar(
            @PathVariable(value = "id") final Long id,
            @RequestBody @Valid final EstadoInputDTO estadoInputDTO
    ) {
        final Estado estadoAtual = this.service.buscarOuFalhar(id);

        this.inputDTODisassembler.copyToDomainObject(estadoInputDTO, estadoAtual);

        final Estado estadoSalvo = this.service.salvar(estadoAtual);

        final EstadoOutputDTO estadoOutputDTO = this.outputDTOAssembler
                .toModel(estadoSalvo);

        return estadoOutputDTO;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void remover(@PathVariable(value = "id") final Long id) {
        this.service.excluir(id);
    }

}
