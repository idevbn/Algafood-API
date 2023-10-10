package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controllers.RestauranteController;
import com.algaworks.algafood.api.model.out.RestauranteBasicoOutputDTO;
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

    @Autowired
    public RestauranteBasicoOutputDTOAssembler(final ModelMapper modelMapper,
                                               final AlgaLinks algaLinks) {
        super(RestauranteController.class, RestauranteBasicoOutputDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
    }

    @Override
    public RestauranteBasicoOutputDTO toModel(final Restaurante restaurante) {
        final RestauranteBasicoOutputDTO restauranteBasicoOutputDTO = createModelWithId(
                restaurante.getId(), restaurante);

        this.modelMapper.map(restaurante, restauranteBasicoOutputDTO);

        restauranteBasicoOutputDTO
                .add(this.algaLinks.linkToRestaurantes("restaurantes"));

        restauranteBasicoOutputDTO.getCozinha().add(
                this.algaLinks.linkToCozinha(restaurante.getCozinha().getId())
        );

        return restauranteBasicoOutputDTO;
    }

    @Override
    public CollectionModel<RestauranteBasicoOutputDTO> toCollectionModel(
            final Iterable<? extends Restaurante> entities
    ) {
        return super.toCollectionModel(entities).add(algaLinks.linkToRestaurantes());
    }

}
