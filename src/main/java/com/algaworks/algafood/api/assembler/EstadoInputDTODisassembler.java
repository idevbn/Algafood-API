package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.in.EstadoInputDTO;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EstadoInputDTODisassembler {

    private final ModelMapper modelMapper;

    public EstadoInputDTODisassembler(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Estado toDomainObject(EstadoInputDTO estadoInputDTO) {
        final Estado estado = this.modelMapper.map(estadoInputDTO, Estado.class);

        return estado;
    }

    public void copyToDomainObject(final EstadoInputDTO estadoInputDTO, final Estado estado) {
        this.modelMapper.map(estadoInputDTO, estado);
    }

}
