package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controllers.CidadeController;
import com.algaworks.algafood.api.controllers.EstadoController;
import com.algaworks.algafood.api.model.out.CidadeOutputDTO;
import com.algaworks.algafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CidadeOutputDTOAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeOutputDTO> {

    private final ModelMapper modelMapper;

    public CidadeOutputDTOAssembler(final ModelMapper modelMapper) {
        super(CidadeController.class, CidadeOutputDTO.class);
        this.modelMapper = modelMapper;
    }

    @Override
    public CidadeOutputDTO toModel(final Cidade cidade) {
        final CidadeOutputDTO cidadeOutputDTO = this.modelMapper
                .map(cidade, CidadeOutputDTO.class);

        cidadeOutputDTO.add(linkTo(methodOn(CidadeController.class)
                .buscar(cidadeOutputDTO.getId()))
                .withSelfRel());

        cidadeOutputDTO.add(linkTo(methodOn(CidadeController.class)
                .listar())
                .withRel("cidades"));

        cidadeOutputDTO.add(linkTo(methodOn(EstadoController.class)
                .buscar(cidadeOutputDTO.getEstado().getId()))
                .withSelfRel());

        return cidadeOutputDTO;
    }

    @Override
    public CollectionModel<CidadeOutputDTO> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(methodOn(CidadeController.class)
                        .listar())
                        .withSelfRel());
    }

}
