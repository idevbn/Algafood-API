package com.algaworks.algafood.core.modelmapper;

import com.algaworks.algafood.api.v1.model.in.ItemPedidoInputDTO;
import com.algaworks.algafood.api.v1.model.out.EnderecoOutputDTO;
import com.algaworks.algafood.api.v2.model.in.CidadeInputDTOV2;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
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

        modelMapper.createTypeMap(CidadeInputDTOV2.class, Cidade.class)
                .addMappings(mapper -> mapper.skip(Cidade::setId));

//        modelMapper.createTypeMap(Restaurante.class, RestauranteOutputDTO.class)
//                .addMapping(Restaurante::getTaxaFrete, RestauranteOutputDTO::setPrecoFrete)
//                .<String>addMapping(
//                src -> src.getEndereco().getCidade().getEstado().getNome(),
//                (dest, value) -> dest.getEndereco().getCidade().setEstado(value));

        final TypeMap<Endereco, EnderecoOutputDTO> enderecoToEnderecoModelTypeMap = modelMapper
                .createTypeMap(Endereco.class, EnderecoOutputDTO.class);

        enderecoToEnderecoModelTypeMap.<String>addMapping(
                enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
                (enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value));

        final TypeMap<ItemPedidoInputDTO, ItemPedido> itemPedidoInputToItemPedidoTypeMap =
                modelMapper.createTypeMap(ItemPedidoInputDTO.class, ItemPedido.class);

        /**
         * Ignora o mapeamento do produtoId para o id do ItemPedido
         */
        itemPedidoInputToItemPedidoTypeMap
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));

        return modelMapper;
    }

}
