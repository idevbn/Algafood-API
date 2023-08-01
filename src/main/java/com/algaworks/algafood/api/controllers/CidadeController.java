package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.api.assembler.CidadeInputDTODisassembler;
import com.algaworks.algafood.api.assembler.CidadeOutputDTOAssembler;
import com.algaworks.algafood.api.model.in.CidadeInputDTO;
import com.algaworks.algafood.api.model.out.CidadeOutputDTO;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Cidades")
@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

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
    @ApiOperation("Lista as cidades")
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
    @ApiOperation("Busca uma cidade por ID")
    public ResponseEntity<CidadeOutputDTO> buscar(@PathVariable(value = "id") final Long id) {
        final Cidade cidadeEncontrada = this.service.buscarOuFalhar(id);

        final CidadeOutputDTO cidadeOutputDTO = this.outputDTOAssembler
                .toModel(cidadeEncontrada);

        final ResponseEntity<CidadeOutputDTO> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(cidadeOutputDTO);

        return response;
    }

    @PostMapping
    @ApiOperation("Cadastra uma cidade")
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
    @ApiOperation("Atualiza uma cidade por ID")
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
    @ApiOperation("Exclui uma cidade por ID")
    public ResponseEntity<Void> remover(@PathVariable(value = "id") Long id) {
        this.service.excluir(id);

        final ResponseEntity<Void> response = ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

        return response;
    }

}
