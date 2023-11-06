package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controllers.EstadoController;
import com.algaworks.algafood.api.v1.model.out.EstadoOutputDTO;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class EstadoOutputDTOAssembler
        extends RepresentationModelAssemblerSupport<Estado, EstadoOutputDTO> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;

    public EstadoOutputDTOAssembler(final ModelMapper modelMapper, final AlgaLinks algaLinks) {
        super(EstadoController.class, EstadoOutputDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
    }

    @Override
    public EstadoOutputDTO toModel(final Estado estado) {
        final EstadoOutputDTO estadoOutputDTO = this
                .createModelWithId(estado.getId(), estado);
        this.modelMapper.map(estado, estadoOutputDTO);

        estadoOutputDTO.add(this.algaLinks.linkToEstados("estados"));

        return estadoOutputDTO;
    }

    @Override
    public CollectionModel<EstadoOutputDTO> toCollectionModel(
            final Iterable<? extends Estado> entities
    ) {
        return super.toCollectionModel(entities).add(algaLinks.linkToEstados());
    }

}
