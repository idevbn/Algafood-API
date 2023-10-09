package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controllers.PedidoController;
import com.algaworks.algafood.api.model.out.PedidoOutputDTO;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoOutputDTOAssembler
        extends RepresentationModelAssemblerSupport<Pedido, PedidoOutputDTO> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;

    public PedidoOutputDTOAssembler(final ModelMapper modelMapper,
                                    final AlgaLinks algaLinks) {
        super(PedidoController.class, PedidoOutputDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
    }

    @Override
    public PedidoOutputDTO toModel(final Pedido pedido) {
        final PedidoOutputDTO pedidoOutputDTO = this.createModelWithId(pedido.getId(), pedido);
        this.modelMapper.map(pedido, pedidoOutputDTO);

        pedidoOutputDTO.add(this.algaLinks.linkToPedidos());

        if (pedido.podeSerConfirmado()) {
            pedidoOutputDTO.add(
                    this.algaLinks.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar")
            );
        }

        if (pedido.podeSerEntregue()) {
            pedidoOutputDTO.add(
                    this.algaLinks.linkToEntregaPedido(pedido.getCodigo(), "entregar")
            );
        }

        if (pedido.podeSerCancelado()) {
            pedidoOutputDTO.add(
                    this.algaLinks.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar")
            );
        }

        pedidoOutputDTO.getRestaurante().add(
                this.algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));

        pedidoOutputDTO.getCliente().add(
                this.algaLinks.linkToUsuario(pedido.getCliente().getId()));

        pedidoOutputDTO.getFormaPagamento().add(
                this.algaLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));

        pedidoOutputDTO.getEnderecoEntrega().getCidade().add(
                this.algaLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));

        pedidoOutputDTO.getItens().forEach(item -> {
            item.add(this.algaLinks.linkToProduto(
                    pedidoOutputDTO.getRestaurante().getId(), item.getProdutoId(), "produto"));
        });

        return pedidoOutputDTO;
    }

}
