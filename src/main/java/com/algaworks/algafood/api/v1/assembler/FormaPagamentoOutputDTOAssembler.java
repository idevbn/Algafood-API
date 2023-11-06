package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controllers.FormaPagamentoController;
import com.algaworks.algafood.api.v1.model.out.FormaPagamentoOutputDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoOutputDTOAssembler
        extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoOutputDTO> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;

    public FormaPagamentoOutputDTOAssembler(final ModelMapper modelMapper,
                                            final AlgaLinks algaLinks) {
        super(FormaPagamentoController.class, FormaPagamentoOutputDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
    }

    @Override
    public FormaPagamentoOutputDTO toModel(final FormaPagamento formaPagamento) {
        final FormaPagamentoOutputDTO formaPagamentoOutputDTO =
                createModelWithId(formaPagamento.getId(), formaPagamento);

        this.modelMapper.map(formaPagamento, formaPagamentoOutputDTO);

        formaPagamentoOutputDTO.add(algaLinks.linkToFormasPagamento("formasPagamento"));

        return formaPagamentoOutputDTO;
    }

    @Override
    public CollectionModel<FormaPagamentoOutputDTO> toCollectionModel(
            final Iterable<? extends FormaPagamento> entities
    ) {
        return super.toCollectionModel(entities)
                .add(this.algaLinks.linkToFormasPagamento());
    }

}
