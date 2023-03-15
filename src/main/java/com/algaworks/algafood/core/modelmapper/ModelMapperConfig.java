package com.algaworks.algafood.core.modelmapper;

import com.algaworks.algafood.api.model.out.RestauranteOutputDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe que configura um {@link ModelMapper} já que esse é um
 * pacote externo ao Spring e não pode ser anotado com @Component.
 *
 * Com essa classe configurada, é possível injetar um {@link ModelMapper}
 * como dependência de outras classes.
 */
@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        final ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(Restaurante.class, RestauranteOutputDTO.class)
                .addMapping(Restaurante::getTaxaFrete, RestauranteOutputDTO::setPrecoFrete)
                .<String>addMapping(
                src -> src.getEndereco().getCidade().getEstado().getNome(),
                (dest, value) -> dest.getEndereco().getCidade().setEstado(value));

        return modelMapper;
    }

}
