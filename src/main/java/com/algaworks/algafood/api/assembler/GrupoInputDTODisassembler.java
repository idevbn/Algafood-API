package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.in.GrupoIdInputDTO;
import com.algaworks.algafood.api.model.in.GrupoInputDTO;
import com.algaworks.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GrupoInputDTODisassembler {

    private final ModelMapper modelMapper;

    public GrupoInputDTODisassembler(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Grupo toDomainObject(final GrupoInputDTO grupoInputDTO) {
        final Grupo grupo = this.modelMapper.map(grupoInputDTO, Grupo.class);

        return grupo;
    }

    public void copyToDomainObject(final GrupoInputDTO grupoInputDTO,
                                   final Grupo grupo) {
        this.modelMapper.map(grupoInputDTO, grupo);
    }

}
