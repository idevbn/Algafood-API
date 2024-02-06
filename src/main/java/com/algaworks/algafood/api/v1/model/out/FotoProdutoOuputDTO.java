package com.algaworks.algafood.api.v1.model.out;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "fotos")
public class FotoProdutoOuputDTO extends RepresentationModel<FotoProdutoOuputDTO> {

    private String nomeArquivo;

    private String descricao;

    private String contentType;

    private Long tamanho;

}
