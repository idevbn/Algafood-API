package com.algaworks.algafood.api.v2.model.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ApiModel("CozinhaInput")
public class CozinhaInputDTOV2 {

    @NotBlank
    @ApiModelProperty(example = "Indiana", required = true)
    private String nomeCozinha;

}
