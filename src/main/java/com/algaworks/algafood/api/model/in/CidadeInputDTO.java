package com.algaworks.algafood.api.model.in;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeInputDTO {
    @NotBlank
    private String nome;
    @Valid
    @NotNull
    private EstadoIdInputDTO estado;

}
