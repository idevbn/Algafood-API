package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.out.PermissaoOutputDTO;
import com.algaworks.algafood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class PermissaoOutputDTOAssembler {

    private final ModelMapper modelMapper;

    public PermissaoOutputDTOAssembler(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PermissaoOutputDTO toModel(final Permissao permissao) {
        final PermissaoOutputDTO permissaoOutputDTO = this.modelMapper
                .map(permissao, PermissaoOutputDTO.class);

        return permissaoOutputDTO;
    }

    public List<PermissaoOutputDTO> toCollectionModel(final Collection<Permissao> permissoes) {
        final List<PermissaoOutputDTO> permissoesOutputDTO = new ArrayList<>();

        for (final Permissao permissao : permissoes) {
            final PermissaoOutputDTO permissaoOutputDTO = this.toModel(permissao);

            permissoesOutputDTO.add(permissaoOutputDTO);
        }

        return permissoesOutputDTO;
    }

}
