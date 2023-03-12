package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.out.FormaPagamentoOutputDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FormaPagamentoOutputDTOAssembler {

    private final ModelMapper modelMapper;

    public FormaPagamentoOutputDTOAssembler(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FormaPagamentoOutputDTO toModel(final FormaPagamento formaPagamento) {
        final FormaPagamentoOutputDTO formaPagamentoOutputDTO = this.modelMapper
                .map(formaPagamento, FormaPagamentoOutputDTO.class);

        return formaPagamentoOutputDTO;
    }

    public List<FormaPagamentoOutputDTO> toCollectionModel(
            final List<FormaPagamento> formasPagamento
    ) {
        final List<FormaPagamentoOutputDTO> formasPagamentoOutputDTOS = new ArrayList<>();

        for (final FormaPagamento formaPagamento : formasPagamento) {
            final FormaPagamentoOutputDTO formaPagamentoOutputDTO = this
                    .toModel(formaPagamento);

            formasPagamentoOutputDTOS.add(formaPagamentoOutputDTO);
        }

        return formasPagamentoOutputDTOS;
    }

}
