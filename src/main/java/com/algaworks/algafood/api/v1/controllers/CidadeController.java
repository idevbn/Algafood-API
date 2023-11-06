package com.algaworks.algafood.api.v1.controllers;

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.v1.assembler.CidadeInputDTODisassembler;
import com.algaworks.algafood.api.v1.assembler.CidadeOutputDTOAssembler;
import com.algaworks.algafood.api.v1.model.in.CidadeInputDTO;
import com.algaworks.algafood.api.v1.model.out.CidadeOutputDTO;
import com.algaworks.algafood.api.v1.openapi.controllers.CidadeControllerOpenApi;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

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
    public ResponseEntity<CollectionModel<CidadeOutputDTO>> listar() {
        final List<Cidade> cidades = this.repository.findAll();

        final CollectionModel<CidadeOutputDTO> cidadesOutputDTOS = this.outputDTOAssembler
                .toCollectionModel(cidades);

        final ResponseEntity<CollectionModel<CidadeOutputDTO>> response = ResponseEntity
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

            /**
              Adiciona o cabeçalho 'Location' na reposta.
              Obs.: O cabeçalho também poderia ser adicio-
              nado no ResponseEntity, no lugar de utilizar
              este método.
             */
            ResourceUriHelper.addUriInReponseHeader(cidadeOutputDTO.getId());

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
