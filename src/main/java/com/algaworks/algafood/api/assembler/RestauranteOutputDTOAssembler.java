package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controllers.RestauranteController;
import com.algaworks.algafood.api.model.out.RestauranteOutputDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * Classe que converte um objeto {@link Restaurante}
 * em um {@link RestauranteOutputDTO}
 */
@Component
public class RestauranteOutputDTOAssembler
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteOutputDTO> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;

    @Autowired
    public RestauranteOutputDTOAssembler(final ModelMapper modelMapper,
                                         final AlgaLinks algaLinks) {
        super(RestauranteController.class, RestauranteOutputDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
    }

    @Override
    public RestauranteOutputDTO toModel(final Restaurante restaurante) {
        final RestauranteOutputDTO restauranteOutputDTO
                = createModelWithId(restaurante.getId(), restaurante);
        this.modelMapper.map(restaurante, restauranteOutputDTO);

        restauranteOutputDTO.add(this.algaLinks.linkToRestaurantes("restaurantes"));

        restauranteOutputDTO.getCozinha().add(
                this.algaLinks.linkToCozinha(restaurante.getCozinha().getId()));

        restauranteOutputDTO.getEndereco().getCidade().add(
                this.algaLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));

        restauranteOutputDTO
                .add(this.algaLinks.linkToRestauranteFormasPagamento(restaurante.getId(),
                        "formas-pagamento"));

        restauranteOutputDTO
                .add(this.algaLinks.linkToResponsaveisRestaurante(restaurante.getId(),
                        "responsaveis"));

        return restauranteOutputDTO;
    }

    @Override
    public CollectionModel<RestauranteOutputDTO> toCollectionModel(
            final Iterable<? extends Restaurante> entities
    ) {
        return super.toCollectionModel(entities).add(algaLinks.linkToRestaurantes());
    }

}
