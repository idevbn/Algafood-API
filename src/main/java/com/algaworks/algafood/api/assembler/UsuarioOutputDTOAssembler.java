package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.out.UsuarioOutputDTO;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class UsuarioOutputDTOAssembler {

    private final ModelMapper modelMapper;

    public UsuarioOutputDTOAssembler(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UsuarioOutputDTO toModel(final Usuario usuario) {
        final UsuarioOutputDTO usuarioOutputDTO = this.modelMapper
                .map(usuario, UsuarioOutputDTO.class);

        return usuarioOutputDTO;
    }

    public List<UsuarioOutputDTO> toCollectionModel(final Collection<Usuario> usuarios) {
        final List<UsuarioOutputDTO> usuariosOutputDTO = new ArrayList<>();

        for (final Usuario usuario : usuarios) {
            final UsuarioOutputDTO usuarioOutputDTO = this.toModel(usuario);

            usuariosOutputDTO.add(usuarioOutputDTO);
        }

        return usuariosOutputDTO;
    }

}
