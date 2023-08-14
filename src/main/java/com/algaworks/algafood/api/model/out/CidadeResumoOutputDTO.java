package com.algaworks.algafood.api.model.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeResumoOutputDTO {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Parahyba")
    private String nome;

    @ApiModelProperty(example = "PB")
    private String estado;

}
