package com.algaworks.algafood.api.v2.assembler;

import com.algaworks.algafood.api.v2.model.in.CidadeInputDTOV2;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CidadeInputDTODisassemblerV2 {

    private final ModelMapper modelMapper;

    public CidadeInputDTODisassemblerV2(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Cidade toDomainObject(final CidadeInputDTOV2 cidadeInput) {
        final Cidade cidade = this.modelMapper.map(cidadeInput, Cidade.class);

        return cidade;
    }

    public void copyToDomainObject(final CidadeInputDTOV2 cidadeInputDTO, final Cidade cidade) {
        // Para evitar org.hibernate.HibernateException: identifier of an instance of
        // com.algaworks.algafood.domain.model.Estado was altered from 1 to 2
        cidade.setEstado(new Estado());

        this.modelMapper.map(cidadeInputDTO, cidade);
    }

}
