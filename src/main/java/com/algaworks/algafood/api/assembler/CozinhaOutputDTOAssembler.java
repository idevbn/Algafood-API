package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controllers.CozinhaController;
import com.algaworks.algafood.api.model.out.CozinhaOutputDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class CozinhaOutputDTOAssembler
        extends RepresentationModelAssemblerSupport<Cozinha, CozinhaOutputDTO> {

    private final ModelMapper modelMapper;

    public CozinhaOutputDTOAssembler(final ModelMapper modelMapper) {
        super(CozinhaController.class, CozinhaOutputDTO.class);
        this.modelMapper = modelMapper;
    }

    @Override
    public CozinhaOutputDTO toModel(final Cozinha cozinha) {
        final CozinhaOutputDTO cozinhaOutputDTO = this.createModelWithId(cozinha.getId(), cozinha);
        this.modelMapper.map(cozinha, cozinhaOutputDTO);

        cozinhaOutputDTO.add(linkTo(CozinhaController.class).withRel("cozinhas"));

        return cozinhaOutputDTO;
    }

}
