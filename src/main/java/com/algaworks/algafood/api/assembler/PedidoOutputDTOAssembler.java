package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.out.PedidoOutputDTO;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class PedidoOutputDTOAssembler {

    private final ModelMapper modelMapper;

    public PedidoOutputDTOAssembler(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PedidoOutputDTO toModel(final Pedido pedido) {
        final PedidoOutputDTO pedidoOutputDTO = this.modelMapper
                .map(pedido, PedidoOutputDTO.class);

        return pedidoOutputDTO;
    }

    public List<PedidoOutputDTO> toCollectionModel(final Collection<Pedido> pedidos) {
        final List<PedidoOutputDTO> pedidosOutputDTOS = new ArrayList<>();

        for (final Pedido pedido : pedidos) {
            final PedidoOutputDTO pedidoOutputDTO = this.toModel(pedido);

            pedidosOutputDTOS.add(pedidoOutputDTO);
        }

        return pedidosOutputDTOS;
    }

}
