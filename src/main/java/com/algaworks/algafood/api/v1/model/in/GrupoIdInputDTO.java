package com.algaworks.algafood.api.v1.model.in;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class GrupoIdInputDTO {

    @NotNull
    private Long id;

}
