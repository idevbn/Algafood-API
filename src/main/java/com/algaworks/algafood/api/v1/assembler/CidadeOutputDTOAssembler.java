package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controllers.CidadeController;
import com.algaworks.algafood.api.v1.model.out.CidadeOutputDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CidadeOutputDTOAssembler
        extends RepresentationModelAssemblerSupport<Cidade, CidadeOutputDTO> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;
    private final AlgaSecurity algaSecurity;

    public CidadeOutputDTOAssembler(final ModelMapper modelMapper,
                                    final AlgaLinks algaLinks,
                                    final AlgaSecurity algaSecurity) {
        super(CidadeController.class, CidadeOutputDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
        this.algaSecurity = algaSecurity;
    }

    @Override
    public CidadeOutputDTO toModel(final Cidade cidade) {
        final CidadeOutputDTO cidadeOutputDTO = this.modelMapper
                .map(cidade, CidadeOutputDTO.class);

        if (this.algaSecurity.podeConsultarCidades()) {
            cidadeOutputDTO.add(this.algaLinks.linkToCidades("cidades"));
        }

        if (this.algaSecurity.podeConsultarEstados()) {
            cidadeOutputDTO.getEstado()
                    .add(this.algaLinks.linkToEstado(cidadeOutputDTO.getEstado().getId()));
        }

        return cidadeOutputDTO;
    }

    @Override
    public CollectionModel<CidadeOutputDTO> toCollectionModel(
            final Iterable<? extends Cidade> entities
    ) {
        final CollectionModel<CidadeOutputDTO> collectionModel = super
                .toCollectionModel(entities);

        if (this.algaSecurity.podeConsultarCidades()) {
            collectionModel.add(this.algaLinks.linkToCidades());
        }

        return collectionModel;
    }

}
