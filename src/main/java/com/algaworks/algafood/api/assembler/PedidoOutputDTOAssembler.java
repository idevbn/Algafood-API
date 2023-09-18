package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controllers.*;
import com.algaworks.algafood.api.model.out.PedidoOutputDTO;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoOutputDTOAssembler
        extends RepresentationModelAssemblerSupport<Pedido, PedidoOutputDTO> {

    private final ModelMapper modelMapper;

    public PedidoOutputDTOAssembler(final ModelMapper modelMapper) {
        super(PedidoController.class, PedidoOutputDTO.class);
        this.modelMapper = modelMapper;
    }

    @Override
    public PedidoOutputDTO toModel(final Pedido pedido) {
        final PedidoOutputDTO pedidoOutputDTO = this.createModelWithId(pedido.getId(), pedido);
        this.modelMapper.map(pedido, pedidoOutputDTO);

        pedidoOutputDTO.add(linkTo(PedidoController.class).withRel("pedidos"));

        pedidoOutputDTO.getRestaurante().add(linkTo(methodOn(RestauranteController.class)
                .buscar(pedido.getRestaurante().getId())).withSelfRel());

        pedidoOutputDTO.getCliente().add(linkTo(methodOn(UsuarioController.class)
                .buscar(pedido.getCliente().getId())).withSelfRel());

        /** Passamos null no segundo argumento, porque é indiferente para a
         * construção da URL do recurso de forma de pagamento.
         */
        pedidoOutputDTO.getFormaPagamento().add(linkTo(methodOn(FormaPagamentoController.class)
                .buscar(pedido.getFormaPagamento().getId(), null)).withSelfRel());

        pedidoOutputDTO.getEnderecoEntrega().getCidade().add(linkTo(methodOn(CidadeController.class)
                .buscar(pedido.getEnderecoEntrega().getCidade().getId())).withSelfRel());

        pedidoOutputDTO.getItens().forEach(item -> {
            item.add(linkTo(methodOn(RestauranteProdutoController.class)
                    .buscar(pedidoOutputDTO.getRestaurante().getId(), item.getProdutoId()))
                    .withRel("produto"));
        });

        return pedidoOutputDTO;
    }

}
