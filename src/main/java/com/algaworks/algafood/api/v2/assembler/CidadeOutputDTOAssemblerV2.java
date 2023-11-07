package com.algaworks.algafood.api.v2.assembler;

import com.algaworks.algafood.api.v2.AlgaLinksV2;
import com.algaworks.algafood.api.v2.controllers.CidadeControllerV2;
import com.algaworks.algafood.api.v2.model.CidadeOutputDTOV2;
import com.algaworks.algafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CidadeOutputDTOAssemblerV2
        extends RepresentationModelAssemblerSupport<Cidade, CidadeOutputDTOV2> {

    private final ModelMapper modelMapper;
    private final AlgaLinksV2 algaLinks;

    public CidadeOutputDTOAssemblerV2(final ModelMapper modelMapper,
                                      final AlgaLinksV2 algaLinks) {
        super(CidadeControllerV2.class, CidadeOutputDTOV2.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
    }

    @Override
    public CidadeOutputDTOV2 toModel(final Cidade cidade) {
        final CidadeOutputDTOV2 cidadeOutputDTO = this.modelMapper
                .map(cidade, CidadeOutputDTOV2.class);

        cidadeOutputDTO.add(this.algaLinks.linkToCidades("cidades"));

        return cidadeOutputDTO;
    }

    @Override
    public CollectionModel<CidadeOutputDTOV2> toCollectionModel(
            final Iterable<? extends Cidade> entities
    ) {
        return super.toCollectionModel(entities).add(this.algaLinks.linkToCidades());
    }

}
