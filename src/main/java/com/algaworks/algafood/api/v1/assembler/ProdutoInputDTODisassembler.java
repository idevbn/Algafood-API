package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.model.in.ProdutoInputDTO;
import com.algaworks.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProdutoInputDTODisassembler {

    private final ModelMapper modelMapper;

    public ProdutoInputDTODisassembler(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Produto toDomainObject(final ProdutoInputDTO produtoInputDTO) {
        final Produto produto = this.modelMapper
                .map(produtoInputDTO, Produto.class);

        return produto;
    }

    public void copyToDomainObject(final ProdutoInputDTO produtoInputDTO,
                                   final Produto produto) {
        this.modelMapper.map(produtoInputDTO, produto);
    }

}
