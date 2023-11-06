package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controllers.GrupoController;
import com.algaworks.algafood.api.v1.model.out.GrupoOutputDTO;
import com.algaworks.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class GrupoOutputDTOAssembler
        extends RepresentationModelAssemblerSupport<Grupo, GrupoOutputDTO> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;

    public GrupoOutputDTOAssembler(final ModelMapper modelMapper,
                                   final AlgaLinks algaLinks) {
        super(GrupoController.class, GrupoOutputDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
    }

    @Override
    public GrupoOutputDTO toModel(final Grupo grupo) {
        final GrupoOutputDTO grupoOutputDTO = createModelWithId(grupo.getId(), grupo);
        this.modelMapper.map(grupo, grupoOutputDTO);

        grupoOutputDTO.add(this.algaLinks.linkToGrupos("grupos"));

        grupoOutputDTO.add(this.algaLinks.linkToGrupoPermissoes(grupo.getId(), "permissoes"));

        return grupoOutputDTO;
    }

    @Override
    public CollectionModel<GrupoOutputDTO> toCollectionModel(
            final Iterable<? extends Grupo> entities
    ) {
        return super.toCollectionModel(entities)
                .add(this.algaLinks.linkToGrupos());
    }

}
