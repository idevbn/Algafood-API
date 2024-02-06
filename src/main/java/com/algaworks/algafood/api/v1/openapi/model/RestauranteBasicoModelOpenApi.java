package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.model.out.CozinhaOutputDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteBasicoModelOpenApi {

    private Long id;

    private String nome;

    private BigDecimal taxaFrete;

    private CozinhaOutputDTO cozinha;

}
