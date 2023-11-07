package com.algaworks.algafood.api.v2.assembler;

import com.algaworks.algafood.api.v2.model.in.CozinhaInputDTOV2;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CozinhaInputDTODisassemblerV2 {

    private final ModelMapper modelMapper;

    public CozinhaInputDTODisassemblerV2(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Cozinha toDomainObject(final CozinhaInputDTOV2 cozinhaInputDTO) {
        final Cozinha cozinha = this.modelMapper.map(cozinhaInputDTO, Cozinha.class);

        return cozinha;
    }

    public void copyToDomainObject(final CozinhaInputDTOV2 cozinhaInputDTO,
                                   final Cozinha cozinha) {
        this.modelMapper.map(cozinhaInputDTO, cozinha);
    }

}
