package com.algaworks.algafood.api.v1.model.in;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CozinhaIdInputDTO {

    @NotNull
    private Long id;

}
