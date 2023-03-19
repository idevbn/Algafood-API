package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.out.PedidoResumoOutputDTO;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class PedidoResumoOutputDTOAssembler {

    private final ModelMapper modelMapper;

    public PedidoResumoOutputDTOAssembler(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PedidoResumoOutputDTO toModel(final Pedido pedido) {
        final PedidoResumoOutputDTO pedidoResumoOutputDTO = this.modelMapper
                .map(pedido, PedidoResumoOutputDTO.class);

        return pedidoResumoOutputDTO;
    }

    public List<PedidoResumoOutputDTO> toCollectionModel(final Collection<Pedido> pedidos) {
        final List<PedidoResumoOutputDTO> pedidosResumoOutputDTOS = new ArrayList<>();

        for (final Pedido pedido : pedidos) {
            final PedidoResumoOutputDTO pedidoResumoOutputDTO = this.toModel(pedido);

            pedidosResumoOutputDTOS.add(pedidoResumoOutputDTO);
        }

        return pedidosResumoOutputDTOS;
    }

}
