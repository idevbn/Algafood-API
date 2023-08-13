package com.algaworks.algafood.api.model.out;

import com.algaworks.algafood.api.model.out.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaOutputDTO {

    @ApiModelProperty(example = "1")
    @JsonView(RestauranteView.Resumo.class)
    private Long id;

    @ApiModelProperty(example = "Indiana")
    @JsonView(RestauranteView.Resumo.class)
    private String nome;

}
