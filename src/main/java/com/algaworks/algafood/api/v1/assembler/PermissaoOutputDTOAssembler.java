package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controllers.PermissaoController;
import com.algaworks.algafood.api.v1.model.out.PermissaoOutputDTO;
import com.algaworks.algafood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PermissaoOutputDTOAssembler
        extends RepresentationModelAssemblerSupport<Permissao, PermissaoOutputDTO> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;

    public PermissaoOutputDTOAssembler(final ModelMapper modelMapper,
                                       final AlgaLinks algaLinks) {
        super(PermissaoController.class, PermissaoOutputDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
    }

    @Override
    public PermissaoOutputDTO toModel(final Permissao permissao) {
        final PermissaoOutputDTO permissaoOutputDTO = this.modelMapper
                .map(permissao, PermissaoOutputDTO.class);

        return permissaoOutputDTO;
    }

    @Override
    public CollectionModel<PermissaoOutputDTO> toCollectionModel(
            final Iterable<? extends Permissao> entities
    ) {
        return PermissaoOutputDTOAssembler.super.toCollectionModel(entities)
                .add(algaLinks.linkToPermissoes());
    }

}
