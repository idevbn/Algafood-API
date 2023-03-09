package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.out.EstadoOutputDTO;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EstadoOutputDTOAssembler {

    private final ModelMapper modelMapper;

    public EstadoOutputDTOAssembler(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public EstadoOutputDTO toModel(final Estado estado) {
        final EstadoOutputDTO estadoOutputDTO = this.modelMapper
                .map(estado, EstadoOutputDTO.class);

        return estadoOutputDTO;
    }

    public List<EstadoOutputDTO> toCollectionModel(final List<Estado> estados) {
        final List<EstadoOutputDTO> estadosOutputDTOS = new ArrayList<>();

        for (final Estado estado : estados) {
            final EstadoOutputDTO restauranteOutputDTO = this.toModel(estado);

            estadosOutputDTOS.add(restauranteOutputDTO);
        }

        return estadosOutputDTOS;
    }

}
