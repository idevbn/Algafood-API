package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controllers.RestauranteController;
import com.algaworks.algafood.api.v1.model.out.RestauranteApenasNomeOutputDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteApenasNomeOutputDTOAssembler
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeOutputDTO> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;
    private final AlgaSecurity algaSecurity;

    @Autowired
    public RestauranteApenasNomeOutputDTOAssembler(final ModelMapper modelMapper,
                                                   final AlgaLinks algaLinks,
                                                   final AlgaSecurity algaSecurity) {
        super(RestauranteController.class, RestauranteApenasNomeOutputDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
        this.algaSecurity = algaSecurity;
    }

    @Override
    public RestauranteApenasNomeOutputDTO toModel(final Restaurante restaurante) {
        final RestauranteApenasNomeOutputDTO restauranteApenasNomeOutputDTO
                = createModelWithId(restaurante.getId(), restaurante);

        this.modelMapper.map(restaurante, restauranteApenasNomeOutputDTO);

        if (this.algaSecurity.podeConsultarRestaurantes()) {
            restauranteApenasNomeOutputDTO
                    .add(algaLinks.linkToRestaurantes("restaurantes"));
        }

        return restauranteApenasNomeOutputDTO;
    }

    @Override
    public CollectionModel<RestauranteApenasNomeOutputDTO> toCollectionModel(
            final Iterable<? extends Restaurante> entities
    ) {
        final CollectionModel<RestauranteApenasNomeOutputDTO> collectionModel = super
                .toCollectionModel(entities);

        if (this.algaSecurity.podeConsultarRestaurantes()) {
            collectionModel.add(this.algaLinks.linkToRestaurantes());
        }

        return collectionModel;
    }

}
