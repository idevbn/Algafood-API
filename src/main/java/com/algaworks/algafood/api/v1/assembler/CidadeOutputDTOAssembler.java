package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controllers.CidadeController;
import com.algaworks.algafood.api.v1.model.out.CidadeOutputDTO;
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

    public CidadeOutputDTOAssembler(final ModelMapper modelMapper,
                                    final AlgaLinks algaLinks) {
        super(CidadeController.class, CidadeOutputDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
    }

    @Override
    public CidadeOutputDTO toModel(final Cidade cidade) {
        final CidadeOutputDTO cidadeOutputDTO = this.modelMapper
                .map(cidade, CidadeOutputDTO.class);

        cidadeOutputDTO.add(this.algaLinks.linkToCidades("cidades"));

        cidadeOutputDTO.getEstado()
                .add(this.algaLinks.linkToEstado(cidadeOutputDTO.getEstado().getId()));

        return cidadeOutputDTO;
    }

    @Override
    public CollectionModel<CidadeOutputDTO> toCollectionModel(
            final Iterable<? extends Cidade> entities
    ) {
        return super.toCollectionModel(entities).add(this.algaLinks.linkToCidades());
    }

}
