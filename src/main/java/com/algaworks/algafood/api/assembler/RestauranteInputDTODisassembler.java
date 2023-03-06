package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.in.RestauranteInputDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * "Desmonta" um objeto de entrada {@link RestauranteInputDTO}
 * convertendo-o na entidade {@link Restaurante}
 */
@Component
public class RestauranteInputDTODisassembler {

    private final ModelMapper modelMapper;

    public RestauranteInputDTODisassembler(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Restaurante toDomainObject(final RestauranteInputDTO restauranteInputDTO) {
        final Restaurante restaurante = this.modelMapper
                .map(restauranteInputDTO, Restaurante.class);

        return restaurante;
    }

}
