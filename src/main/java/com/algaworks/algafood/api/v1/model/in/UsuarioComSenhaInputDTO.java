package com.algaworks.algafood.api.v1.model.in;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioComSenhaInputDTO extends UsuarioInputDTO {

    @NotBlank
    private String senha;

}
