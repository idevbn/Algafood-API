package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.api.assembler.CidadeInputDTODisassembler;
import com.algaworks.algafood.api.assembler.CidadeOutputDTOAssembler;
import com.algaworks.algafood.api.controllers.openapi.CidadeControllerOpenApi;
import com.algaworks.algafood.api.model.in.CidadeInputDTO;
import com.algaworks.algafood.api.model.out.CidadeOutputDTO;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

    private final CidadeRepository repository;
    private final CadastroCidadeService service;
    private final CidadeInputDTODisassembler inputDTODisassembler;
    private final CidadeOutputDTOAssembler outputDTOAssembler;

    @Autowired
    public CidadeController(final CidadeRepository repository,
                            final CadastroCidadeService service,
                            final CidadeInputDTODisassembler inputDTODisassembler,
                            final CidadeOutputDTOAssembler outputDTOAssembler) {
        this.repository = repository;
        this.service = service;
        this.inputDTODisassembler = inputDTODisassembler;
        this.outputDTOAssembler = outputDTOAssembler;
    }

    @GetMapping
    public ResponseEntity<List<CidadeOutputDTO>> listar() {
        final List<Cidade> cidades = this.repository.findAll();

        final List<CidadeOutputDTO> cidadesOutputDTOS = this.outputDTOAssembler
                .toCollectionModel(cidades);

        final ResponseEntity<List<CidadeOutputDTO>> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(cidadesOutputDTOS);

        return response;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CidadeOutputDTO> buscar(
            @PathVariable(value = "id") final Long id
    ) {
        final Cidade cidadeEncontrada = this.service.buscarOuFalhar(id);

        final CidadeOutputDTO cidadeOutputDTO = this.outputDTOAssembler
                .toModel(cidadeEncontrada);

        final ResponseEntity<CidadeOutputDTO> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(cidadeOutputDTO);

        return response;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<CidadeOutputDTO> adicionar(
            @RequestBody @Valid final CidadeInputDTO cidadeInputDTO
    ) {
        try {
            final Cidade cidade = this.inputDTODisassembler
                    .toDomainObject(cidadeInputDTO);

            final Cidade cidadeSalva = this.service.salvar(cidade);

            final CidadeOutputDTO cidadeOutputDTO = this.outputDTOAssembler
                    .toModel(cidadeSalva);

            final ResponseEntity<CidadeOutputDTO> response = ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(cidadeOutputDTO);

            return response;
        } catch (final EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CidadeOutputDTO> atualizar(
            @PathVariable(value = "id") final Long id,
            @RequestBody @Valid final CidadeInputDTO cidadeInputDTO
    ) {
        final Cidade cidadeAtual = this.service.buscarOuFalhar(id);

        this.inputDTODisassembler.copyToDomainObject(cidadeInputDTO, cidadeAtual);

        try {
            final Cidade cidadeSalva = this.service.salvar(cidadeAtual);

            final CidadeOutputDTO cidadeOutputDTO = this.outputDTOAssembler
                    .toModel(cidadeSalva);

            final ResponseEntity<CidadeOutputDTO> response = ResponseEntity
                    .status(HttpStatus.OK)
                    .body(cidadeOutputDTO);

            return response;
        } catch (final EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> remover(@PathVariable(value = "id") final Long id) {
        this.service.excluir(id);

        return ResponseEntity.noContent().build();
    }

}
