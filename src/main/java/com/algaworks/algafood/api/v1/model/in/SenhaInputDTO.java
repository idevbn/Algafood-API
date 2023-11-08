package com.algaworks.algafood.api.v1.model.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SenhaInputDTO {

    @NotBlank
    @ApiModelProperty(example = "123", required = true)
    private String senhaAtual;

    @NotBlank
    @ApiModelProperty(example = "123", required = true)
    private String novaSenha;

}