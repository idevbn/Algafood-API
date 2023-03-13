package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.out.GrupoOutputDTO;
import com.algaworks.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GrupoOutputDTOAssembler {

    private final ModelMapper modelMapper;

    public GrupoOutputDTOAssembler(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public GrupoOutputDTO toModel(final Grupo grupo) {
        final GrupoOutputDTO grupoOutputDTO = this.modelMapper
                .map(grupo, GrupoOutputDTO.class);

        return grupoOutputDTO;
    }

    public List<GrupoOutputDTO> toCollectionModel(final List<Grupo> grupos) {
        final ArrayList<GrupoOutputDTO> gruposOutputDTO = new ArrayList<>();

        for (final Grupo grupo : grupos) {
            final GrupoOutputDTO grupoOutputDTO = this.toModel(grupo);

            gruposOutputDTO.add(grupoOutputDTO);
        }

        return gruposOutputDTO;
    }

}
