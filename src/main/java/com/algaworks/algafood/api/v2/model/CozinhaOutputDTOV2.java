package com.algaworks.algafood.api.v2.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "cozinhas")
public class CozinhaOutputDTOV2 extends RepresentationModel<CozinhaOutputDTOV2> {

    private Long idCozinha;

    private String nomeCozinha;

}
