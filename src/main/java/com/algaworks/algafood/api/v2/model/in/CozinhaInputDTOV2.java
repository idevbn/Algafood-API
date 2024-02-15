package com.algaworks.algafood.api.v2.model.in;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class CozinhaInputDTOV2 {

    @NotBlank
    private String nomeCozinha;

}
