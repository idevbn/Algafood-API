package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controllers.RestauranteFotoProdutoController;
import com.algaworks.algafood.api.v1.model.out.FotoProdutoOuputDTO;
import com.algaworks.algafood.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoOutputDTOAssembler
        extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoOuputDTO> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;

    public FotoProdutoOutputDTOAssembler(final ModelMapper modelMapper,
                                         final AlgaLinks algaLinks) {
        super(RestauranteFotoProdutoController.class, FotoProdutoOuputDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
    }

    @Override
    public FotoProdutoOuputDTO toModel(final FotoProduto fotoProduto) {
        final FotoProdutoOuputDTO fotoProdutoOuputDTO =
                this.modelMapper.map(fotoProduto, FotoProdutoOuputDTO.class);

        fotoProdutoOuputDTO.add(this.algaLinks.linkToFotoProduto(
                fotoProduto.getRestauranteId(), fotoProduto.getProduto().getId()));

        fotoProdutoOuputDTO.add(this.algaLinks.linkToProduto(
                fotoProduto.getRestauranteId(), fotoProduto.getProduto().getId(), "produto"));

        return fotoProdutoOuputDTO;
    }

}
