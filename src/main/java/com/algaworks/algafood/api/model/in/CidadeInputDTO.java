package com.algaworks.algafood.api.model.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeInputDTO {

    @NotBlank
    @ApiModelProperty(example = "Parahyba")
    private String nome;

    @Valid
    @NotNull
    private EstadoIdInputDTO estado;

}
