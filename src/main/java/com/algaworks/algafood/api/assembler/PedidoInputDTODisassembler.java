package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.in.PedidoInputDTO;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PedidoInputDTODisassembler {

    private final ModelMapper modelMapper;

    public PedidoInputDTODisassembler(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Pedido toDomainModel(final PedidoInputDTO pedidoInputDTO) {
        final Pedido pedido = this.modelMapper.map(pedidoInputDTO, Pedido.class);

        return pedido;
    }

    public void copyToDomainModel(final PedidoInputDTO pedidoInputDTO,
                                  final Pedido pedido) {
        this.modelMapper.map(pedidoInputDTO, pedido);
    }

}
