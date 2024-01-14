package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controllers.RestauranteFotoProdutoController;
import com.algaworks.algafood.api.v1.model.out.FotoProdutoOuputDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoOutputDTOAssembler
        extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoOuputDTO> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;
    private final AlgaSecurity algaSecurity;

    public FotoProdutoOutputDTOAssembler(final ModelMapper modelMapper,
                                         final AlgaLinks algaLinks,
                                         final AlgaSecurity algaSecurity) {
        super(RestauranteFotoProdutoController.class, FotoProdutoOuputDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
        this.algaSecurity = algaSecurity;
    }

    @Override
    public FotoProdutoOuputDTO toModel(final FotoProduto fotoProduto) {
        final FotoProdutoOuputDTO fotoProdutoOuputDTO =
                this.modelMapper.map(fotoProduto, FotoProdutoOuputDTO.class);

        // Quem pode consultar restaurantes, também pode consultar os produtos e fotos
        if (this.algaSecurity.podeConsultarRestaurantes()) {
            fotoProdutoOuputDTO.add(this.algaLinks.linkToFotoProduto(
                    fotoProduto.getRestauranteId(), fotoProduto.getProduto().getId()));

            fotoProdutoOuputDTO.add(this.algaLinks.linkToProduto(
                    fotoProduto.getRestauranteId(), fotoProduto.getProduto().getId(), "produto"));
        }

        return fotoProdutoOuputDTO;
    }

}
