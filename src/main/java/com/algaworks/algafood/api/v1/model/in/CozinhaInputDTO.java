package com.algaworks.algafood.api.v1.model.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CozinhaInputDTO {

    @NotBlank
    @ApiModelProperty(example = "Indiana", required = true)
    private String nome;

}
