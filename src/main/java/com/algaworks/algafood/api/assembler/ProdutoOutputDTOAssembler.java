package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.out.ProdutoOutputDTO;
import com.algaworks.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProdutoOutputDTOAssembler {

    private final ModelMapper modelMapper;

    public ProdutoOutputDTOAssembler(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProdutoOutputDTO toModel(final Produto produto) {
        final ProdutoOutputDTO produtoOutputDTO = this.modelMapper
                .map(produto, ProdutoOutputDTO.class);

        return  produtoOutputDTO;
    }

    public List<ProdutoOutputDTO> toCollectionModel(final List<Produto> produtos) {
        final List<ProdutoOutputDTO> produtosOutputDTO = new ArrayList<>();

        for (final Produto produto : produtos) {
            final ProdutoOutputDTO produtoOutputDTO = this.toModel(produto);

            produtosOutputDTO.add(produtoOutputDTO);
        }

        return produtosOutputDTO;
    }

}
