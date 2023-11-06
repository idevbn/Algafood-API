package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.model.in.UsuarioInputDTO;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UsuarioInputDTODisassembler {

    private final ModelMapper modelMapper;

    public UsuarioInputDTODisassembler(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Usuario toDomainObject(final UsuarioInputDTO usuarioInputDTO) {
        final Usuario usuario = this.modelMapper.map(usuarioInputDTO, Usuario.class);

        return usuario;
    }

    public void copyToDomainObject(final UsuarioInputDTO usuarioInputDTO,
                                   final Usuario usuario) {
        this.modelMapper.map(usuarioInputDTO, usuario);
    }

}
