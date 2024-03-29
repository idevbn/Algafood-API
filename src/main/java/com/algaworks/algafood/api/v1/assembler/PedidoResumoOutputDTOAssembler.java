package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controllers.PedidoController;
import com.algaworks.algafood.api.v1.model.out.PedidoResumoOutputDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoResumoOutputDTOAssembler
        extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoOutputDTO> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;
    private final AlgaSecurity algaSecurity;

    public PedidoResumoOutputDTOAssembler(final ModelMapper modelMapper,
                                          final AlgaLinks algaLinks,
                                          final AlgaSecurity algaSecurity) {
        super(PedidoController.class, PedidoResumoOutputDTO.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
        this.algaSecurity = algaSecurity;
    }

    @Override
    public PedidoResumoOutputDTO toModel(final Pedido pedido) {
        final PedidoResumoOutputDTO pedidoResumoOutputDTO = this
                .createModelWithId(pedido.getId(), pedido);
        this.modelMapper.map(pedido, pedidoResumoOutputDTO);

        if (this.algaSecurity.podePesquisarPedidos()) {
            pedidoResumoOutputDTO.add(this.algaLinks.linkToPedidos("pedidos"));
        }

        if (this.algaSecurity.podeConsultarRestaurantes()) {
            pedidoResumoOutputDTO.getRestaurante()
                    .add(this.algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));
        }

        if (this.algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            pedidoResumoOutputDTO.getCliente()
                    .add(this.algaLinks.linkToUsuario(pedido.getCliente().getId()));
        }

        return pedidoResumoOutputDTO;
    }

}
