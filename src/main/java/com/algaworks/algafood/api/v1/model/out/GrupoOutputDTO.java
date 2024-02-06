package com.algaworks.algafood.api.v1.model.out;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "grupos")
public class GrupoOutputDTO extends RepresentationModel<GrupoOutputDTO> {

    private Long id;

    private String nome;

}
