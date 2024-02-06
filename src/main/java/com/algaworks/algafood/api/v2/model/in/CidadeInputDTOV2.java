package com.algaworks.algafood.api.v2.model.in;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeInputDTOV2 {

    @NotBlank
    private String nomeCidade;

    @NotNull
    private Long idEstado;

}
