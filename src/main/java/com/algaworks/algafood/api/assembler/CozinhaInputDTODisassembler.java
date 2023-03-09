package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.in.CozinhaInputDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CozinhaInputDTODisassembler {

    private final ModelMapper modelMapper;

    public CozinhaInputDTODisassembler(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Cozinha toDomainObject(final CozinhaInputDTO cozinhaInputDTO) {
        final Cozinha cozinha = this.modelMapper.map(cozinhaInputDTO, Cozinha.class);

        return cozinha;
    }

    public void copyToDomainObject(final CozinhaInputDTO cozinhaInputDTO,
                                   final Cozinha cozinha) {
        this.modelMapper.map(cozinhaInputDTO, cozinha);
    }

}
