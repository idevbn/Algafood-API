package com.algaworks.algafood.api.model.in;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FormaPagamentoIdInputDTO {

    @NotNull
    private Long id;

}