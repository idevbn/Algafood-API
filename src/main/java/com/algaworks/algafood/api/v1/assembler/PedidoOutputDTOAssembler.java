package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controllers.PedidoController;
import com.algaworks.algafood.api.v1.model.out.PedidoOutputDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoOutputDTOAssembler
        extends RepresentationModelAssemblerSupport<Pedido, PedidoOutputDTO> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;
    private final AlgaSecurity algaSecurity;

    public PedidoOutputDTOAssembler(final ModelMapper modelMapper,
                                    final AlgaLinks algaLinks,
                                    final AlgaSecurity algaSecurity) {
        super(PedidoController.class, PedidoOutputDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
        this.algaSecurity = algaSecurity;
    }

    @Override
    public PedidoOutputDTO toModel(final Pedido pedido) {
        final PedidoOutputDTO pedidoOutputDTO = this.createModelWithId(pedido.getId(), pedido);
        this.modelMapper.map(pedido, pedidoOutputDTO);

        if (this.algaSecurity.podePesquisarPedidos()) {
            pedidoOutputDTO.add(this.algaLinks.linkToPedidos("pedidos"));
        }

        if (this.algaSecurity.podeGerenciarPedidos(pedido.getCodigo())) {
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
        }

        if (this.algaSecurity.podeConsultarRestaurantes()) {
            pedidoOutputDTO.getRestaurante().add(
                    this.algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));
        }

        if (this.algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            pedidoOutputDTO.getCliente().add(
                    this.algaLinks.linkToUsuario(pedido.getCliente().getId()));
        }

        if (this.algaSecurity.podeConsultarFormasPagamento()) {
            pedidoOutputDTO.getFormaPagamento().add(
                    this.algaLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
        }

        if (this.algaSecurity.podeConsultarCidades()) {
            pedidoOutputDTO.getEnderecoEntrega().getCidade().add(
                    this.algaLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));
        }

        // Quem pode consultar restaurantes, também pode consultar os produtos dos restaurantes
        if (this.algaSecurity.podeConsultarRestaurantes()) {
            pedidoOutputDTO.getItens().forEach(item -> {
                item.add(this.algaLinks.linkToProduto(
                        pedidoOutputDTO.getRestaurante().getId(), item.getProdutoId(), "produto"));
            });
        }

        return pedidoOutputDTO;
    }

}
