package com.algaworks.algafood.api.model.out;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteOutputDTO {

    private Long id;
    private String nome;
    private BigDecimal precoFrete;
    private CozinhaOutputDTO cozinha;
    private Boolean ativo;
    private Boolean aberto;
    private EnderecoOutputDTO endereco;

}
