package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controllers.EstadoController;
import com.algaworks.algafood.api.model.out.EstadoOutputDTO;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class EstadoOutputDTOAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoOutputDTO> {

    private final ModelMapper modelMapper;

    public EstadoOutputDTOAssembler(final ModelMapper modelMapper) {
        super(EstadoController.class, EstadoOutputDTO.class);
        this.modelMapper = modelMapper;
    }

    @Override
    public EstadoOutputDTO toModel(final Estado estado) {
        final EstadoOutputDTO estadoOutputDTO = this
                .createModelWithId(estado.getId(), estado);
        this.modelMapper.map(estado, estadoOutputDTO);

        estadoOutputDTO.add(linkTo(EstadoController.class).withRel("estados"));

        return estadoOutputDTO;
    }

    @Override
    public CollectionModel<EstadoOutputDTO> toCollectionModel(Iterable<? extends Estado> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(EstadoController.class).withSelfRel());
    }
}
