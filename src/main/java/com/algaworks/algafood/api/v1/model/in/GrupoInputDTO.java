package com.algaworks.algafood.api.v1.model.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class GrupoInputDTO {

    @NotBlank
    @Schema(example = "Gerente")
    private String nome;

}
