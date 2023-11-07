package com.algaworks.algafood.api.v2.assembler;

import com.algaworks.algafood.api.v2.AlgaLinksV2;
import com.algaworks.algafood.api.v2.controllers.CozinhaControllerV2;
import com.algaworks.algafood.api.v2.model.CozinhaOutputDTOV2;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaOutputDTOAssemblerV2
        extends RepresentationModelAssemblerSupport<Cozinha, CozinhaOutputDTOV2> {

    private final ModelMapper modelMapper;
    private final AlgaLinksV2 algaLinks;

    public CozinhaOutputDTOAssemblerV2(final ModelMapper modelMapper,
                                       final AlgaLinksV2 algaLinks) {
        super(CozinhaControllerV2.class, CozinhaOutputDTOV2.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
    }

    @Override
    public CozinhaOutputDTOV2 toModel(final Cozinha cozinha) {
        final CozinhaOutputDTOV2 cozinhaOutputDTO = this.createModelWithId(cozinha.getId(), cozinha);
        this.modelMapper.map(cozinha, cozinhaOutputDTO);

        cozinhaOutputDTO.add(this.algaLinks.linkToCozinhas("cozinhas"));

        return cozinhaOutputDTO;
    }

}
