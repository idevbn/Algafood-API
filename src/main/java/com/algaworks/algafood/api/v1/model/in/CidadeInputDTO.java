package com.algaworks.algafood.api.v1.model.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeInputDTO {

    @NotBlank
    @Schema(example = "Parahyba")
    private String nome;

    @Valid
    @NotNull
    private EstadoIdInputDTO estado;

}
