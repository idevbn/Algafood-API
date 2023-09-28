package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controllers.CozinhaController;
import com.algaworks.algafood.api.model.out.CozinhaOutputDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaOutputDTOAssembler
        extends RepresentationModelAssemblerSupport<Cozinha, CozinhaOutputDTO> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;

    public CozinhaOutputDTOAssembler(final ModelMapper modelMapper,
                                     final AlgaLinks algaLinks) {
        super(CozinhaController.class, CozinhaOutputDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
    }

    @Override
    public CozinhaOutputDTO toModel(final Cozinha cozinha) {
        final CozinhaOutputDTO cozinhaOutputDTO = this.createModelWithId(cozinha.getId(), cozinha);
        this.modelMapper.map(cozinha, cozinhaOutputDTO);

        cozinhaOutputDTO.add(this.algaLinks.linkToCozinhas("cozinhas"));

        return cozinhaOutputDTO;
    }

}
