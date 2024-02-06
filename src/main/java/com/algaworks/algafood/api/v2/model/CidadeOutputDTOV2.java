package com.algaworks.algafood.api.v2.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "cidades")
public class CidadeOutputDTOV2 extends RepresentationModel<CidadeOutputDTOV2> {

    private Long idCidade;

    private String nomeCidade;

    private Long idEstado;

    private String nomeEstado;

}
