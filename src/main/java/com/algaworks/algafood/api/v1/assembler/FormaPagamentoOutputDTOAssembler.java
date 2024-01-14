package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controllers.FormaPagamentoController;
import com.algaworks.algafood.api.v1.model.out.FormaPagamentoOutputDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
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
    private final AlgaSecurity algaSecurity;

    public FormaPagamentoOutputDTOAssembler(final ModelMapper modelMapper,
                                            final AlgaLinks algaLinks,
                                            final AlgaSecurity algaSecurity) {
        super(FormaPagamentoController.class, FormaPagamentoOutputDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
        this.algaSecurity = algaSecurity;
    }

    @Override
    public FormaPagamentoOutputDTO toModel(final FormaPagamento formaPagamento) {
        final FormaPagamentoOutputDTO formaPagamentoOutputDTO =
                createModelWithId(formaPagamento.getId(), formaPagamento);

        this.modelMapper.map(formaPagamento, formaPagamentoOutputDTO);

        if (this.algaSecurity.podeConsultarFormasPagamento()) {
            formaPagamentoOutputDTO
                    .add(algaLinks.linkToFormasPagamento("formasPagamento"));
        }

        return formaPagamentoOutputDTO;
    }

    @Override
    public CollectionModel<FormaPagamentoOutputDTO> toCollectionModel(
            final Iterable<? extends FormaPagamento> entities
    ) {
        final CollectionModel<FormaPagamentoOutputDTO> collectionModel = super
                .toCollectionModel(entities);

        if (this.algaSecurity.podeConsultarFormasPagamento()) {
            collectionModel.add(this.algaLinks.linkToFormasPagamento());
        }

        return collectionModel;
    }

}
