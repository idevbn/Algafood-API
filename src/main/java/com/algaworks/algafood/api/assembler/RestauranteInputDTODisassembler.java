package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.in.RestauranteInputDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.stereotype.Component;

/**
 * "Desmonta" um objeto de entrada {@link RestauranteInputDTO}
 * convertendo-o na entidade {@link Restaurante}
 */
@Component
public class RestauranteInputDTODisassembler {

    public Restaurante toDomainObject(final RestauranteInputDTO restauranteInputDTO) {
        final Restaurante restaurante = new Restaurante();

        restaurante.setNome(restauranteInputDTO.getNome());
        restaurante.setTaxaFrete(restauranteInputDTO.getTaxaFrete());

        final Cozinha cozinha = new Cozinha();
        cozinha.setId(restauranteInputDTO.getCozinha().getId());

        restaurante.setCozinha(cozinha);

        return restaurante;
    }

}
