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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public List<CidadeOutputDTO> listar() {
        final List<Cidade> cidades = this.repository.findAll();

        final List<CidadeOutputDTO> cidadesOutputDTOS = this.outputDTOAssembler
                .toCollectionModel(cidades);

        return cidadesOutputDTOS;
    }

    @GetMapping(value = "/{id}")
    public CidadeOutputDTO buscar(@PathVariable(value = "id") final Long id) {
        final Cidade cidadeEncontrada = this.service.buscarOuFalhar(id);

        final CidadeOutputDTO cidadeOutputDTO = this.outputDTOAssembler.toModel(cidadeEncontrada);

        return cidadeOutputDTO;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CidadeOutputDTO adicionar(@RequestBody @Valid final CidadeInputDTO cidadeInputDTO) {
        try {
            final Cidade cidade = this.inputDTODisassembler
                    .toDomainObject(cidadeInputDTO);

            final Cidade cidadeSalva = this.service.salvar(cidade);

            final CidadeOutputDTO cidadeOutputDTO = this.outputDTOAssembler
                    .toModel(cidadeSalva);

            return cidadeOutputDTO;
        } catch (final EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping(value = "/{id}")
    public CidadeOutputDTO atualizar(
            @PathVariable(value = "id") final Long id,
            @RequestBody @Valid final CidadeInputDTO cidadeInputDTO
    ) {
        final Cidade cidadeAtual = this.service.buscarOuFalhar(id);

        this.inputDTODisassembler.copyToDomainObject(cidadeInputDTO, cidadeAtual);

        try {
            final Cidade cidadeSalva = this.service.salvar(cidadeAtual);

            final CidadeOutputDTO cidadeOutputDTO = this.outputDTOAssembler
                    .toModel(cidadeSalva);

            return cidadeOutputDTO;
        } catch (final EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void remover(@PathVariable(value = "id") Long id) {
        this.service.excluir(id);
    }

}
