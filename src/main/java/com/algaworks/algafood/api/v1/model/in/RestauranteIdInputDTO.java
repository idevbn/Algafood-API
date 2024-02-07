package com.algaworks.algafood.api.v1.model.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RestauranteIdInputDTO {

    @NotNull
    @Schema(example = "1")
    private Long id;

}
