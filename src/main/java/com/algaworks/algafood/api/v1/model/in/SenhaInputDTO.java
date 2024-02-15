package com.algaworks.algafood.api.v1.model.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class SenhaInputDTO {

    @NotBlank
    @Schema(example = "123", type = "string")
    private String senhaAtual;

    @NotBlank
    @Schema(example = "123", type = "string")
    private String novaSenha;

}
