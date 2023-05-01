package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.out.FotoProdutoOuputDTO;
import com.algaworks.algafood.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoOutputDTOAssembler {

    private final ModelMapper modelMapper;

    public FotoProdutoOutputDTOAssembler(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FotoProdutoOuputDTO toModel(final FotoProduto fotoProduto) {
        final FotoProdutoOuputDTO fotoProdutoOuputDTO = this.modelMapper
                .map(fotoProduto, FotoProdutoOuputDTO.class);

        return fotoProdutoOuputDTO;
    }

}
