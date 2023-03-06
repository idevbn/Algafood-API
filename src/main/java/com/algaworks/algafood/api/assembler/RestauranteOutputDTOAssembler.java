package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.out.RestauranteOutputDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que converte um objeto {@link Restaurante}
 * em um {@link RestauranteOutputDTO}
 */
@Component
public class RestauranteOutputDTOAssembler {

    private final ModelMapper modelMapper;

    @Autowired
    public RestauranteOutputDTOAssembler(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RestauranteOutputDTO toModel(final Restaurante restaurante) {
        final RestauranteOutputDTO restauranteOutputDTO = this.modelMapper
                .map(restaurante, RestauranteOutputDTO.class);

        return restauranteOutputDTO;
    }

    public List<RestauranteOutputDTO> toCollectionModel(final List<Restaurante> restaurantes) {
        final List<RestauranteOutputDTO> restaurantesOutputDTO = new ArrayList<>();

        for (final Restaurante restaurante : restaurantes) {
            final RestauranteOutputDTO restauranteOutputDTO = this.toModel(restaurante);

            restaurantesOutputDTO.add(restauranteOutputDTO);
        }

        return restaurantesOutputDTO;
    }

}
