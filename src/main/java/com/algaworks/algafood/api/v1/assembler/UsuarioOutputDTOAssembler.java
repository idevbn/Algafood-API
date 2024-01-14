package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controllers.UsuarioController;
import com.algaworks.algafood.api.v1.model.out.UsuarioOutputDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class UsuarioOutputDTOAssembler
        extends RepresentationModelAssemblerSupport<Usuario, UsuarioOutputDTO> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;
    private final AlgaSecurity algaSecurity;

    public UsuarioOutputDTOAssembler(final ModelMapper modelMapper,
                                     final AlgaLinks algaLinks,
                                     final AlgaSecurity algaSecurity) {
        super(UsuarioController.class, UsuarioOutputDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
        this.algaSecurity = algaSecurity;
    }

    @Override
    public UsuarioOutputDTO toModel(final Usuario usuario) {
        final UsuarioOutputDTO usuarioOutputDTO = this
                .createModelWithId(usuario.getId(), usuario);

        this.modelMapper.map(usuario, usuarioOutputDTO);

        if (this.algaSecurity.podeConsultarUsuariosGruposPermissoes()) {

            usuarioOutputDTO.add(this.algaLinks.linkToUsuarios("usuarios"));

            usuarioOutputDTO
                    .add(this.algaLinks.linkToGruposUsuario(usuario.getId(), "grupos-usuario"));
        }

        return usuarioOutputDTO;
    }

    @Override
    public CollectionModel<UsuarioOutputDTO> toCollectionModel(
            final Iterable<? extends Usuario> entities
    ) {
        return super.toCollectionModel(entities).add(algaLinks.linkToUsuarios());
    }

}
