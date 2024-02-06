package com.algaworks.algafood.api.v1.model.out;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Getter
@Setter
@Relation(collectionRelation = "restaurantes")
public class RestauranteBasicoOutputDTO extends RepresentationModel<RestauranteBasicoOutputDTO> {

    private Long id;

    private String nome;

    private BigDecimal taxaFrete;

    private CozinhaOutputDTO cozinha;
    private Boolean ativo;
    private Boolean aberto;
    private EnderecoOutputDTO endereco;


}

