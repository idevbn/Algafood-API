package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controllers.UsuarioController;
import com.algaworks.algafood.api.controllers.UsuarioGrupoController;
import com.algaworks.algafood.api.model.out.UsuarioOutputDTO;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioOutputDTOAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioOutputDTO> {

    private final ModelMapper modelMapper;

    public UsuarioOutputDTOAssembler(final ModelMapper modelMapper) {
        super(UsuarioController.class, UsuarioOutputDTO.class);
        this.modelMapper = modelMapper;
    }

    @Override
    public UsuarioOutputDTO toModel(final Usuario usuario) {
        final UsuarioOutputDTO usuarioOutputDTO = this.modelMapper
                .map(usuario, UsuarioOutputDTO.class);

        usuarioOutputDTO.add(linkTo(methodOn(UsuarioController.class)
                .buscar(usuarioOutputDTO.getId()))
                .withSelfRel());

        usuarioOutputDTO.add(linkTo(methodOn(UsuarioController.class)
                .listar())
                .withRel("usuarios"));

        usuarioOutputDTO.add(linkTo(methodOn(UsuarioGrupoController.class)
                .listar(usuarioOutputDTO.getId()))
                .withRel("grupos-usuario"));

        return usuarioOutputDTO;
    }

    @Override
    public CollectionModel<UsuarioOutputDTO> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(UsuarioController.class).withSelfRel());
    }
}
