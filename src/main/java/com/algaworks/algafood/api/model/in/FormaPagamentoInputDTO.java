package com.algaworks.algafood.api.model.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class FormaPagamentoInputDTO {

    @NotBlank
    @ApiModelProperty(example = "Cartão de crédito", required = true)
    private String descricao;

}
