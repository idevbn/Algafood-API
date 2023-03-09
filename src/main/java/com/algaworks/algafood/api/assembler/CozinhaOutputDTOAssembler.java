package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.out.CozinhaOutputDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CozinhaOutputDTOAssembler {

    private final ModelMapper modelMapper;

    public CozinhaOutputDTOAssembler(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CozinhaOutputDTO toModel(final Cozinha cozinha) {
        final CozinhaOutputDTO cozinhaOutputDTO = this.modelMapper
                .map(cozinha, CozinhaOutputDTO.class);

        return cozinhaOutputDTO;
    }

    public List<CozinhaOutputDTO> toCollectionModel(final List<Cozinha> cozinhas) {
        final List<CozinhaOutputDTO> cozinhasOutputDTOS = new ArrayList<>();

        for (final Cozinha cozinha : cozinhas) {
            final CozinhaOutputDTO cozinhaOutputDTO = this.toModel(cozinha);

            cozinhasOutputDTOS.add(cozinhaOutputDTO);
        }

        return cozinhasOutputDTOS;
    }

}
