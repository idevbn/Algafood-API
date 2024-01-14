package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controllers.RestauranteProdutoController;
import com.algaworks.algafood.api.v1.model.out.ProdutoOutputDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProdutoOutputDTOAssembler
        extends RepresentationModelAssemblerSupport<Produto, ProdutoOutputDTO> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;
    private final AlgaSecurity algaSecurity;

    public ProdutoOutputDTOAssembler(final ModelMapper modelMapper,
                                     final AlgaLinks algaLinks,
                                     final AlgaSecurity algaSecurity) {
        super(RestauranteProdutoController.class, ProdutoOutputDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
        this.algaSecurity = algaSecurity;
    }

    @Override
    public ProdutoOutputDTO toModel(final Produto produto) {
        final ProdutoOutputDTO produtoOutputDTO = createModelWithId(
                produto.getId(), produto, produto.getRestaurante().getId()
        );

        this.modelMapper.map(produto, produtoOutputDTO);

        if (this.algaSecurity.podeConsultarRestaurantes()) {

            produtoOutputDTO.add(this.algaLinks.linkToProdutos(
                    produto.getRestaurante().getId(), "produtos")
            );

            produtoOutputDTO.add(this.algaLinks.linkToFotoProduto(
                    produto.getRestaurante().getId(), produto.getId(), "foto"));
        }

        return produtoOutputDTO;
    }

}
