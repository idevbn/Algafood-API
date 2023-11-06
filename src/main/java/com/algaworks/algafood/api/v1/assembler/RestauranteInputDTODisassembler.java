package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.model.in.RestauranteInputDTO;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
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

    /**
     * Faz a cópia de um {@link RestauranteInputDTO},
     * recebendo
     * @param restauranteInputDTO
     * @param restaurante
     * para um {@link Restaurante}.
     *
     * É atribuído um novo valor para a cozinha pois:
     *
     * Caso o  {@link Restaurante} seja criado e atribuído
     * na criação uma {@link Cozinha} de id = 1, caso na atualização
     * a referência da {@link Cozinha} seja alterada para outro id,
     * será disparado um erro 500 pois o JPA entendende que tentou-se
     * alterar um id, e não uma referência.
     */
    public void copyToDomainObject(
            final RestauranteInputDTO restauranteInputDTO,
            final Restaurante restaurante) {
        restaurante.setCozinha(new Cozinha());

        if (restaurante.getEndereco() != null) {
            restaurante.getEndereco().setCidade(new Cidade());
        }

        this.modelMapper.map(restauranteInputDTO, restaurante);
    }

}
