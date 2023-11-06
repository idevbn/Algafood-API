package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controllers.RestauranteController;
import com.algaworks.algafood.api.v1.model.out.RestauranteOutputDTO;
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

        if (restaurante.ativacaoPermitida()) {
            restauranteOutputDTO.add(
                    algaLinks.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
        }

        if (restaurante.inativacaoPermitida()) {
            restauranteOutputDTO.add(
                    algaLinks.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
        }

        if (restaurante.aberturaPermitida()) {
            restauranteOutputDTO.add(
                    algaLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
        }

        if (restaurante.fechamentoPermitido()) {
            restauranteOutputDTO.add(
                    algaLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
        }

        restauranteOutputDTO.add(this.algaLinks.linkToProdutos(restaurante.getId(), "produtos"));

        restauranteOutputDTO.getCozinha().add(
                this.algaLinks.linkToCozinha(restaurante.getCozinha().getId()));

        if (restaurante.getEndereco() != null && restaurante.getEndereco().getCidade() != null) {
            restauranteOutputDTO.getEndereco().getCidade().add(
                    this.algaLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
        }
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
