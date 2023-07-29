package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.api.assembler.FormaPagamentoInputDTODisassembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoOutputDTOAssembler;
import com.algaworks.algafood.api.model.in.FormaPagamentoInputDTO;
import com.algaworks.algafood.api.model.out.FormaPagamentoOutputDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    public ResponseEntity<List<FormaPagamentoOutputDTO>> listar(final ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        final OffsetDateTime ultimaDataAtualizacao = this.repository.getUltimaDataAtualizacao();

        if (ultimaDataAtualizacao != null) {
            eTag = String.valueOf(ultimaDataAtualizacao.toEpochSecond());
        }

        /**
         * Já temos condições de saber se haverá ou não o processamento
         */
        if (request.checkNotModified(eTag)) {
            return null;
        }

        final List<FormaPagamento> formasPagamento = this.repository.findAll();

        final List<FormaPagamentoOutputDTO> formasPagamentoOutputDTOS = this
                .outputDTOAssembler.toCollectionModel(formasPagamento);

        final ResponseEntity<List<FormaPagamentoOutputDTO>> response = ResponseEntity
                .status(HttpStatus.OK)
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .eTag(eTag)
                .body(formasPagamentoOutputDTOS);

        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamentoOutputDTO> buscar(@PathVariable("id") final Long id,
                                                          final ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        final OffsetDateTime ultimaDataAtualizacao = this.repository.getUltimaDataAtualizacao();

        if (ultimaDataAtualizacao != null) {
            eTag = String.valueOf(ultimaDataAtualizacao.toEpochSecond());
        }

        /**
         * Já temos condições de saber se haverá ou não o processamento
         */
        if (request.checkNotModified(eTag)) {
            return null;
        }

        final FormaPagamento formaPagamento = this.service.buscarOuFalhar(id);

        final FormaPagamentoOutputDTO formaPagamentoOutputDTO = this
                .outputDTOAssembler.toModel(formaPagamento);

        final ResponseEntity<FormaPagamentoOutputDTO> response = ResponseEntity
                .status(HttpStatus.OK)
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
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
