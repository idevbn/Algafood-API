package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controllers.CozinhaController;
import com.algaworks.algafood.api.v1.model.out.CozinhaOutputDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaOutputDTOAssembler
        extends RepresentationModelAssemblerSupport<Cozinha, CozinhaOutputDTO> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;
    private final AlgaSecurity algaSecurity;

    public CozinhaOutputDTOAssembler(final ModelMapper modelMapper,
                                     final AlgaLinks algaLinks,
                                     final AlgaSecurity algaSecurity) {
        super(CozinhaController.class, CozinhaOutputDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
        this.algaSecurity = algaSecurity;
    }

    @Override
    public CozinhaOutputDTO toModel(final Cozinha cozinha) {
        final CozinhaOutputDTO cozinhaOutputDTO = this.createModelWithId(cozinha.getId(), cozinha);
        this.modelMapper.map(cozinha, cozinhaOutputDTO);

        if (this.algaSecurity.podeConsultarCozinhas()) {
            cozinhaOutputDTO.add(this.algaLinks.linkToCozinhas("cozinhas"));
        }

        return cozinhaOutputDTO;
    }

}
