package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.out.CidadeOutputDTO;
import com.algaworks.algafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CidadeOutputDTOAssembler {

    private final ModelMapper modelMapper;

    public CidadeOutputDTOAssembler(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CidadeOutputDTO toModel(final Cidade cidade) {
        final CidadeOutputDTO cidadeOutputDTO = this.modelMapper
                .map(cidade, CidadeOutputDTO.class);

        return cidadeOutputDTO;
    }

    public List<CidadeOutputDTO> toCollectionModel(final List<Cidade> cidades) {
        final List<CidadeOutputDTO> cidadesOutputDTOS = new ArrayList<>();

        for (final Cidade cidade : cidades) {
            final CidadeOutputDTO cidadeOutputDTO = this.toModel(cidade);

            cidadesOutputDTOS.add(cidadeOutputDTO);
        }

        return cidadesOutputDTOS;
    }

}
