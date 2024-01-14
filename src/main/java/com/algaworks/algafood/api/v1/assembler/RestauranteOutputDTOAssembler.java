package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controllers.RestauranteController;
import com.algaworks.algafood.api.v1.model.out.RestauranteOutputDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
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
    private final AlgaSecurity algaSecurity;

    @Autowired
    public RestauranteOutputDTOAssembler(final ModelMapper modelMapper,
                                         final AlgaLinks algaLinks,
                                         final AlgaSecurity algaSecurity) {
        super(RestauranteController.class, RestauranteOutputDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
        this.algaSecurity = algaSecurity;
    }

    @Override
    public RestauranteOutputDTO toModel(final Restaurante restaurante) {
        final RestauranteOutputDTO restauranteOutputDTO
                = createModelWithId(restaurante.getId(), restaurante);
        this.modelMapper.map(restaurante, restauranteOutputDTO);

        if (this.algaSecurity.podeConsultarRestaurantes()) {
            restauranteOutputDTO.add(this.algaLinks.linkToRestaurantes("restaurantes"));
        }

        if (this.algaSecurity.podeGerenciarCadastroRestaurantes()) {
            if (restaurante.ativacaoPermitida()) {
                restauranteOutputDTO.add(
                        algaLinks.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
            }

            if (restaurante.inativacaoPermitida()) {
                restauranteOutputDTO.add(
                        algaLinks.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
            }
        }

        if (this.algaSecurity.podeGerenciarFuncionamentoRestaurantes(restaurante.getId())) {
            if (restaurante.aberturaPermitida()) {
                restauranteOutputDTO.add(
                        this.algaLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
            }

            if (restaurante.fechamentoPermitido()) {
                restauranteOutputDTO.add(
                        this.algaLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
            }
        }

        if (this.algaSecurity.podeConsultarRestaurantes()) {
            restauranteOutputDTO
                    .add(this.algaLinks.linkToProdutos(restaurante.getId(), "produtos"));
        }

        if (this.algaSecurity.podeConsultarCozinhas()) {
            restauranteOutputDTO.getCozinha().add(
                    this.algaLinks.linkToCozinha(restaurante.getCozinha().getId()));
        }

        if (this.algaSecurity.podeConsultarCidades()) {
            if (restaurante.getEndereco() != null && restaurante.getEndereco().getCidade() != null) {
                restauranteOutputDTO.getEndereco().getCidade().add(
                        this.algaLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
            }
        }

        if (this.algaSecurity.podeConsultarRestaurantes()) {
            restauranteOutputDTO
                    .add(this.algaLinks.linkToRestauranteFormasPagamento(restaurante.getId(),
                            "formas-pagamento"));
        }

        if (this.algaSecurity.podeGerenciarCadastroRestaurantes()) {
            restauranteOutputDTO
                    .add(this.algaLinks.linkToResponsaveisRestaurante(restaurante.getId(),
                            "responsaveis"));
        }

        return restauranteOutputDTO;
    }

    @Override
    public CollectionModel<RestauranteOutputDTO> toCollectionModel(
            final Iterable<? extends Restaurante> entities
    ) {
        final CollectionModel<RestauranteOutputDTO> collectionModel = super
                .toCollectionModel(entities);

        if (this.algaSecurity.podeConsultarRestaurantes()) {
            collectionModel.add(this.algaLinks.linkToRestaurantes());
        }

        return collectionModel;
    }

}
