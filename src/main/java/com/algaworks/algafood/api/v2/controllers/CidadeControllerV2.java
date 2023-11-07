package com.algaworks.algafood.api.v2.controllers;

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.v2.assembler.CidadeInputDTODisassemblerV2;
import com.algaworks.algafood.api.v2.assembler.CidadeOutputDTOAssemblerV2;
import com.algaworks.algafood.api.v2.model.CidadeOutputDTOV2;
import com.algaworks.algafood.api.v2.model.in.CidadeInputDTOV2;
import com.algaworks.algafood.api.v2.openapi.controllers.CidadeControllerV2OpenApi;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/v2/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeControllerV2 implements CidadeControllerV2OpenApi {

    private final CidadeRepository repository;
    private final CadastroCidadeService service;
    private final CidadeInputDTODisassemblerV2 inputDTODisassembler;
    private final CidadeOutputDTOAssemblerV2 outputDTOAssembler;

    @Autowired
    public CidadeControllerV2(final CidadeRepository repository,
                              final CadastroCidadeService service,
                              final CidadeInputDTODisassemblerV2 inputDTODisassembler,
                              final CidadeOutputDTOAssemblerV2 outputDTOAssembler) {
        this.repository = repository;
        this.service = service;
        this.inputDTODisassembler = inputDTODisassembler;
        this.outputDTOAssembler = outputDTOAssembler;
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionModel<CidadeOutputDTOV2>> listar() {
        final List<Cidade> cidades = this.repository.findAll();

        final CollectionModel<CidadeOutputDTOV2> cidadesOutputDTOS = this.outputDTOAssembler
                .toCollectionModel(cidades);

        final ResponseEntity<CollectionModel<CidadeOutputDTOV2>> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(cidadesOutputDTOS);

        return response;
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CidadeOutputDTOV2> buscar(
            @PathVariable(value = "id") final Long id
    ) {
        final Cidade cidadeEncontrada = this.service.buscarOuFalhar(id);

        final CidadeOutputDTOV2 cidadeOutputDTO = this.outputDTOAssembler
                .toModel(cidadeEncontrada);

        final ResponseEntity<CidadeOutputDTOV2> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(cidadeOutputDTO);

        return response;
    }

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<CidadeOutputDTOV2> adicionar(
            @RequestBody @Valid final CidadeInputDTOV2 cidadeInputDTO
    ) {
        try {
            final Cidade cidade = this.inputDTODisassembler
                    .toDomainObject(cidadeInputDTO);

            final Cidade cidadeSalva = this.service.salvar(cidade);

            final CidadeOutputDTOV2 cidadeOutputDTO = this.outputDTOAssembler
                    .toModel(cidadeSalva);

            final ResponseEntity<CidadeOutputDTOV2> response = ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(cidadeOutputDTO);

            /**
              Adiciona o cabeçalho 'Location' na reposta.
              Obs.: O cabeçalho também poderia ser adicio-
              nado no ResponseEntity, no lugar de utilizar
              este método.
             */
            ResourceUriHelper.addUriInReponseHeader(cidadeOutputDTO.getIdCidade());

            return response;
        } catch (final EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CidadeOutputDTOV2> atualizar(
            @PathVariable(value = "id") final Long id,
            @RequestBody @Valid final CidadeInputDTOV2 cidadeInputDTO
    ) {
        final Cidade cidadeAtual = this.service.buscarOuFalhar(id);

        this.inputDTODisassembler.copyToDomainObject(cidadeInputDTO, cidadeAtual);

        try {
            final Cidade cidadeSalva = this.service.salvar(cidadeAtual);

            final CidadeOutputDTOV2 cidadeOutputDTO = this.outputDTOAssembler
                    .toModel(cidadeSalva);

            final ResponseEntity<CidadeOutputDTOV2> response = ResponseEntity
                    .status(HttpStatus.OK)
                    .body(cidadeOutputDTO);

            return response;
        } catch (final EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> remover(@PathVariable(value = "id") final Long id) {
        this.service.excluir(id);

        return ResponseEntity.noContent().build();
    }

}
