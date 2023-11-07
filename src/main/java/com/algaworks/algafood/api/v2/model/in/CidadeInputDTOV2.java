package com.algaworks.algafood.api.v2.model.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel("CidadeInput")
public class CidadeInputDTOV2 {

    @NotBlank
    @ApiModelProperty(example = "Parahyba", required = true)
    private String nomeCidade;

    @NotNull
    @ApiModelProperty(example = "1", required = true)
    private Long idEstado;

}
