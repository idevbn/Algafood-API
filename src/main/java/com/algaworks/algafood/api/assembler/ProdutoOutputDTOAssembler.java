package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controllers.RestauranteProdutoController;
import com.algaworks.algafood.api.model.out.ProdutoOutputDTO;
import com.algaworks.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProdutoOutputDTOAssembler
        extends RepresentationModelAssemblerSupport<Produto, ProdutoOutputDTO> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;

    public ProdutoOutputDTOAssembler(final ModelMapper modelMapper,
                                     final AlgaLinks algaLinks) {
        super(RestauranteProdutoController.class, ProdutoOutputDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
    }

    @Override
    public ProdutoOutputDTO toModel(final Produto produto) {
        final ProdutoOutputDTO produtoOutputDTO = createModelWithId(
                produto.getId(), produto, produto.getRestaurante().getId()
        );

        this.modelMapper.map(produto, produtoOutputDTO);

        produtoOutputDTO.add(this.algaLinks.linkToProdutos(
                produto.getRestaurante().getId(), "produtos")
        );

        return produtoOutputDTO;
    }

}
