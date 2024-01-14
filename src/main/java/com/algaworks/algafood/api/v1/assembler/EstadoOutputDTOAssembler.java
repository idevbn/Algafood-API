package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controllers.EstadoController;
import com.algaworks.algafood.api.v1.model.out.EstadoOutputDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
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
    private final AlgaSecurity algaSecurity;

    public EstadoOutputDTOAssembler(final ModelMapper modelMapper,
                                    final AlgaLinks algaLinks,
                                    final AlgaSecurity algaSecurity) {
        super(EstadoController.class, EstadoOutputDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
        this.algaSecurity = algaSecurity;
    }

    @Override
    public EstadoOutputDTO toModel(final Estado estado) {
        final EstadoOutputDTO estadoOutputDTO = this
                .createModelWithId(estado.getId(), estado);
        this.modelMapper.map(estado, estadoOutputDTO);

        if (this.algaSecurity.podeConsultarEstados()) {
            estadoOutputDTO.add(this.algaLinks.linkToEstados("estados"));
        }

        return estadoOutputDTO;
    }

    @Override
    public CollectionModel<EstadoOutputDTO> toCollectionModel(
            final Iterable<? extends Estado> entities
    ) {
        final CollectionModel<EstadoOutputDTO> collectionModel = super
                .toCollectionModel(entities);

        if (this.algaSecurity.podeConsultarEstados()) {
            collectionModel.add(this.algaLinks.linkToEstados("estados"));
        }

        return collectionModel;
    }

}
