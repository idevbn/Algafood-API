package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.out.CozinhaOutputDTO;
import com.algaworks.algafood.api.model.out.RestauranteOutputDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que converte um objeto {@link Restaurante}
 * em um {@link RestauranteOutputDTO}
 */
@Component
public class RestauranteOutputDTOAssembler {

    public RestauranteOutputDTO toModel(final Restaurante restaurante) {
        final CozinhaOutputDTO cozinhaDTO = new CozinhaOutputDTO();
        cozinhaDTO.setId(restaurante.getCozinha().getId());
        cozinhaDTO.setNome(restaurante.getCozinha().getNome());

        final RestauranteOutputDTO restauranteOutputDTO = new RestauranteOutputDTO();
        restauranteOutputDTO.setId(restaurante.getId());
        restauranteOutputDTO.setNome(restaurante.getNome());
        restauranteOutputDTO.setTaxaFrete(restaurante.getTaxaFrete());
        restauranteOutputDTO.setCozinha(cozinhaDTO);
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
