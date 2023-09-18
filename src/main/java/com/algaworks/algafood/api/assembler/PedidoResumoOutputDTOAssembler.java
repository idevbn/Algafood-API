package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controllers.PedidoController;
import com.algaworks.algafood.api.controllers.RestauranteController;
import com.algaworks.algafood.api.controllers.UsuarioController;
import com.algaworks.algafood.api.model.out.PedidoResumoOutputDTO;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoResumoOutputDTOAssembler
        extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoOutputDTO> {

    private final ModelMapper modelMapper;

    public PedidoResumoOutputDTOAssembler(final ModelMapper modelMapper) {
        super(PedidoController.class, PedidoResumoOutputDTO.class);
        this.modelMapper = modelMapper;
    }

    @Override
    public PedidoResumoOutputDTO toModel(final Pedido pedido) {
        final PedidoResumoOutputDTO pedidoResumoOutputDTO = this
                .createModelWithId(pedido.getId(), pedido);
        this.modelMapper.map(pedido, pedidoResumoOutputDTO);

        pedidoResumoOutputDTO.add(linkTo(PedidoController.class).withRel("pedidos"));

        pedidoResumoOutputDTO.getRestaurante().add(linkTo(methodOn(RestauranteController.class)
                .buscar(pedido.getRestaurante().getId())).withSelfRel());

        pedidoResumoOutputDTO.getCliente().add(linkTo(methodOn(UsuarioController.class)
                .buscar(pedido.getCliente().getId())).withSelfRel());

        return pedidoResumoOutputDTO;
    }

}
