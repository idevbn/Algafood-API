package com.algaworks.algafood.api.model.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioComSenhaInputDTO extends UsuarioInputDTO {

    @NotBlank
    @ApiModelProperty(example = "123", required = true)
    private String senha;

}
