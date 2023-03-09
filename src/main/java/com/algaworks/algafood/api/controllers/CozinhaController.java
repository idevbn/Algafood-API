package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.api.assembler.CozinhaInputDTODisassembler;
import com.algaworks.algafood.api.assembler.CozinhaOutputDTOAssembler;
import com.algaworks.algafood.api.model.in.CozinhaInputDTO;
import com.algaworks.algafood.api.model.out.CozinhaOutputDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

    private final CozinhaRepository repository;
    private final CadastroCozinhaService service;
    private final CozinhaInputDTODisassembler inputDTODisassembler;
    private final CozinhaOutputDTOAssembler outputDTOAssembler;

    @Autowired
    public CozinhaController(final CozinhaRepository repository,
                             final CadastroCozinhaService service,
                             final CozinhaInputDTODisassembler inputDTODisassembler,
                             final CozinhaOutputDTOAssembler outputDTOAssembler) {
        this.repository = repository;
        this.service = service;
        this.inputDTODisassembler = inputDTODisassembler;
        this.outputDTOAssembler = outputDTOAssembler;
    }


    @GetMapping
    public List<CozinhaOutputDTO> listar() {
        final List<Cozinha> cozinhas = this.repository.findAll();

        final List<CozinhaOutputDTO> cozinhasOutputDTOS = this.outputDTOAssembler
                .toCollectionModel(cozinhas);

        return cozinhasOutputDTOS;
    }

    @GetMapping(value = "/{id}")
    public CozinhaOutputDTO buscar(@PathVariable(value = "id") final Long id) {
        final Cozinha cozinha = this.service.buscarOuFalhar(id);

        final CozinhaOutputDTO cozinhaOutputDTO = this.outputDTOAssembler.toModel(cozinha);

        return cozinhaOutputDTO;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CozinhaOutputDTO adicionar(@RequestBody @Valid final CozinhaInputDTO cozinhaInputDTO) {
        final Cozinha cozinha = this.inputDTODisassembler
                .toDomainObject(cozinhaInputDTO);

        final Cozinha cozinhaSalva = this.service.salvar(cozinha);

        final CozinhaOutputDTO cozinhaOutputDTO = this.outputDTOAssembler
                .toModel(cozinhaSalva);

        return cozinhaOutputDTO;
    }

    @PutMapping(value = "/{id}")
    public CozinhaOutputDTO atualizar(
            @PathVariable("id") Long id,
            @RequestBody @Valid final CozinhaInputDTO cozinhaInputDTO
    ) {
        final Cozinha cozinhaAtual = this.service.buscarOuFalhar(id);

        this.inputDTODisassembler.copyToDomainObject(cozinhaInputDTO, cozinhaAtual);

        final Cozinha cozinhaSalva = this.service.salvar(cozinhaAtual);

        final CozinhaOutputDTO cozinhaOutputDTO = this.outputDTOAssembler
                .toModel(cozinhaSalva);

        return cozinhaOutputDTO;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void remover(@PathVariable("id") Long id) {
        this.service.excluir(id);
    }

}
