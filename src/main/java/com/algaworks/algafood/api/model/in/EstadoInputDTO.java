package com.algaworks.algafood.api.model.in;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EstadoInputDTO {
    @NotBlank
    private String nome;

}
