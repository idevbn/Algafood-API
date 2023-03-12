package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.api.assembler.FormaPagamentoInputDTODisassembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoOutputDTOAssembler;
import com.algaworks.algafood.api.model.in.FormaPagamentoInputDTO;
import com.algaworks.algafood.api.model.out.FormaPagamentoOutputDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/formas-pagamento")
public class FormaPagamentoController {

    private final CadastroFormaPagamentoService service;
    private final FormaPagamentoRepository repository;
    private final FormaPagamentoInputDTODisassembler inputDTODisassembler;
    private final FormaPagamentoOutputDTOAssembler outputDTOAssembler;

    public FormaPagamentoController(final CadastroFormaPagamentoService service,
                                    final FormaPagamentoRepository repository,
                                    final FormaPagamentoInputDTODisassembler inputDTODisassembler,
                                    final FormaPagamentoOutputDTOAssembler outputDTOAssembler) {
        this.service = service;
        this.repository = repository;
        this.inputDTODisassembler = inputDTODisassembler;
        this.outputDTOAssembler = outputDTOAssembler;
    }

    @GetMapping
    public ResponseEntity<List<FormaPagamentoOutputDTO>> listar() {
        final List<FormaPagamento> formasPagamento = this.repository.findAll();

        final List<FormaPagamentoOutputDTO> formasPagamentoOutputDTOS = this
                .outputDTOAssembler.toCollectionModel(formasPagamento);

        final ResponseEntity<List<FormaPagamentoOutputDTO>> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(formasPagamentoOutputDTOS);

        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamentoOutputDTO> buscar(@PathVariable("id") final Long id) {
        final FormaPagamento formaPagamento = this.service.buscarOuFalhar(id);

        final FormaPagamentoOutputDTO formaPagamentoOutputDTO = this
                .outputDTOAssembler.toModel(formaPagamento);

        final ResponseEntity<FormaPagamentoOutputDTO> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(formaPagamentoOutputDTO);

        return response;
    }

    @PostMapping
    public ResponseEntity<FormaPagamentoOutputDTO> adicionar(
            @RequestBody @Valid final FormaPagamentoInputDTO formaPagamentoInputDTO
    ) {
        final FormaPagamento formaPagamento = this
                .inputDTODisassembler.toDomainObject(formaPagamentoInputDTO);

        final FormaPagamento formaPagamentoSalva = this.service.salvar(formaPagamento);

        final FormaPagamentoOutputDTO formaPagamentoOutputDTO = this
                .outputDTOAssembler.toModel(formaPagamentoSalva);

        final ResponseEntity<FormaPagamentoOutputDTO> response = ResponseEntity
                .status(HttpStatus.CREATED)
                .body(formaPagamentoOutputDTO);

        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<FormaPagamentoOutputDTO>  atualizar(
            @PathVariable("id") final Long id,
            @RequestBody @Valid final FormaPagamentoInputDTO formaPagamentoInputDTO
    ) {
        final FormaPagamento formaPagamentoAtual = this.service.buscarOuFalhar(id);

        this.inputDTODisassembler
                .copyToDomainObject(formaPagamentoInputDTO, formaPagamentoAtual);

        final FormaPagamento formaPagamentoSalva = this.service.salvar(formaPagamentoAtual);

        final FormaPagamentoOutputDTO formaPagamentoOutputDTO = this
                .outputDTOAssembler.toModel(formaPagamentoSalva);

        final ResponseEntity<FormaPagamentoOutputDTO> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(formaPagamentoOutputDTO);

        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable("id") final Long id) {
        this.service.excluir(id);

        final ResponseEntity<Void> response = ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

        return response;
    }

}
