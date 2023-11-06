package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.model.out.CozinhaOutputDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Api("RestauranteBasicoModel")
public class RestauranteBasicoModelOpenApi {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "New Indian Cuisine")
    private String nome;

    @ApiModelProperty(example = "12.85")
    private BigDecimal taxaFrete;

    private CozinhaOutputDTO cozinha;

}
