package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.api.assembler.FormaPagamentoOutputDTOAssembler;
import com.algaworks.algafood.api.model.out.FormaPagamentoOutputDTO;
import com.algaworks.algafood.api.openapi.controllers.RestauranteFormaPagamentoControllerOpenApi;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/restaurantes/{id}/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

    private final CadastroRestauranteService service;
    private final FormaPagamentoOutputDTOAssembler assembler;

    @Autowired
    public RestauranteFormaPagamentoController(final CadastroRestauranteService service,
                                               final FormaPagamentoOutputDTOAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }
    
    @GetMapping
    public ResponseEntity<CollectionModel<FormaPagamentoOutputDTO>> listar(
            @PathVariable("id") final Long id
    ) {
        final Restaurante restauranteEncontrado = this.service.buscarOuFalhar(id);

        final Collection<FormaPagamento> formasPagamento = restauranteEncontrado
                .getFormasPagamento();

        final CollectionModel<FormaPagamentoOutputDTO> formasPagamentoOutputDTOS = this.assembler
                .toCollectionModel(formasPagamento);

        final ResponseEntity<CollectionModel<FormaPagamentoOutputDTO>> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(formasPagamentoOutputDTOS);

        return response;
    }

    @PutMapping(value = "/{formaPagamentoId}")
    public ResponseEntity<Void> associar(
            @PathVariable("id") final Long id,
            @PathVariable("formaPagamentoId") final Long formaPagamentoId
    ) {
        this.service.associarFormaPagamento(id, formaPagamentoId);

        final ResponseEntity<Void> response = ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

        return response;
    }

    @DeleteMapping(value = "/{formaPagamentoId}")
    public ResponseEntity<Void> desassociar(
            @PathVariable("id") final Long id,
            @PathVariable("formaPagamentoId") final Long formaPagamentoId
    ) {
       this.service.desassociarFormaPagamento(id, formaPagamentoId);

        final ResponseEntity<Void> response = ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

        return response;
    }

}