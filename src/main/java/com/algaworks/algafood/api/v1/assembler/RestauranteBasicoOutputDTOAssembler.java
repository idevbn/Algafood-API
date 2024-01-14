package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controllers.RestauranteController;
import com.algaworks.algafood.api.v1.model.out.RestauranteBasicoOutputDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteBasicoOutputDTOAssembler
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoOutputDTO> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;
    private final AlgaSecurity algaSecurity;

    @Autowired
    public RestauranteBasicoOutputDTOAssembler(final ModelMapper modelMapper,
                                               final AlgaLinks algaLinks,
                                               final AlgaSecurity algaSecurity) {
        super(RestauranteController.class, RestauranteBasicoOutputDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
        this.algaSecurity = algaSecurity;
    }

    @Override
    public RestauranteBasicoOutputDTO toModel(final Restaurante restaurante) {
        final RestauranteBasicoOutputDTO restauranteBasicoOutputDTO = createModelWithId(
                restaurante.getId(), restaurante);

        this.modelMapper.map(restaurante, restauranteBasicoOutputDTO);

        if (this.algaSecurity.podeConsultarRestaurantes()) {
            restauranteBasicoOutputDTO
                    .add(this.algaLinks.linkToRestaurantes("restaurantes"));
        }

        if (this.algaSecurity.podeConsultarCozinhas()) {
            restauranteBasicoOutputDTO.getCozinha().add(
                    this.algaLinks.linkToCozinha(restaurante.getCozinha().getId())
            );
        }

        return restauranteBasicoOutputDTO;
    }

    @Override
    public CollectionModel<RestauranteBasicoOutputDTO> toCollectionModel(
            final Iterable<? extends Restaurante> entities
    ) {
        final CollectionModel<RestauranteBasicoOutputDTO> collectionModel = super
                .toCollectionModel(entities);

        if (this.algaSecurity.podeConsultarRestaurantes()) {
            collectionModel.add(this.algaLinks.linkToRestaurantes());
        }

        return collectionModel;
    }

}
