package com.algaworks.algafood.api.v1.controllers;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.FormaPagamentoOutputDTOAssembler;
import com.algaworks.algafood.api.v1.model.out.FormaPagamentoOutputDTO;
import com.algaworks.algafood.api.v1.openapi.controllers.RestauranteFormaPagamentoControllerOpenApi;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.CheckSecurity;
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
@RequestMapping(path = "/v1/restaurantes/{id}/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

    private final CadastroRestauranteService service;
    private final FormaPagamentoOutputDTOAssembler assembler;
    private final AlgaLinks algaLinks;
    private final AlgaSecurity algaSecurity;

    @Autowired
    public RestauranteFormaPagamentoController(final CadastroRestauranteService service,
                                               final FormaPagamentoOutputDTOAssembler assembler,
                                               final AlgaLinks algaLinks,
                                               final AlgaSecurity algaSecurity) {
        this.service = service;
        this.assembler = assembler;
        this.algaLinks = algaLinks;
        this.algaSecurity = algaSecurity;
    }

    @GetMapping
    @CheckSecurity.Restaurantes.PodeConsultar
    public ResponseEntity<CollectionModel<FormaPagamentoOutputDTO>> listar(
            @PathVariable("id") final Long id
    ) {
        final Restaurante restauranteEncontrado = this.service.buscarOuFalhar(id);

        final Collection<FormaPagamento> formasPagamento = restauranteEncontrado
                .getFormasPagamento();

        final CollectionModel<FormaPagamentoOutputDTO> formasPagamentoOutputDTOS = this.assembler
                .toCollectionModel(formasPagamento)
                .removeLinks()
                .add(this.algaLinks.linkToRestauranteFormasPagamento(id));

        if (this.algaSecurity.podeGerenciarFuncionamentoRestaurantes(id)) {
            formasPagamentoOutputDTOS
                    .add(this.algaLinks.linkToRestauranteFormaPagamentoAssociacao(id, "associar"));

            formasPagamentoOutputDTOS.getContent().forEach(formaPagamentoOutputDTO -> {
                formaPagamentoOutputDTO.add(this.algaLinks.linkToRestauranteFormaPagamentoDesassociacao(
                                id,
                                formaPagamentoOutputDTO.getId(),
                                "desassociar"
                        )
                );
            });
        }

        final ResponseEntity<CollectionModel<FormaPagamentoOutputDTO>> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(formasPagamentoOutputDTOS);

        return response;
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
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

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
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