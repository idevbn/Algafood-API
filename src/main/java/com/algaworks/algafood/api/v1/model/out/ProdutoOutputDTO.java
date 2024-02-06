package com.algaworks.algafood.api.v1.model.out;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Getter
@Setter
@Relation(collectionRelation = "produtos")
public class ProdutoOutputDTO extends RepresentationModel<ProdutoOutputDTO> {

    private Long id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private Boolean ativo;

}
