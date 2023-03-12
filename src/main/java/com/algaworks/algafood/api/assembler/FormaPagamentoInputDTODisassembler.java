package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.in.FormaPagamentoInputDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoInputDTODisassembler {

    private final ModelMapper modelMapper;

    public FormaPagamentoInputDTODisassembler(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FormaPagamento toDomainObject(final FormaPagamentoInputDTO formaPagamentoInputDTO) {
        final FormaPagamento formaPagamento = this.modelMapper
                .map(formaPagamentoInputDTO, FormaPagamento.class);

        return formaPagamento;
    }

    public void copyToDomainObject(final FormaPagamentoInputDTO formaPagamentoInputDTO,
                                   final FormaPagamento formaPagamento) {
        this.modelMapper.map(formaPagamentoInputDTO, formaPagamento);
    }

}
