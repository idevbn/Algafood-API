package com.algaworks.algafood.api.v2.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@ApiModel(value = "CidadeModel")
@Relation(collectionRelation = "cidades")
public class CidadeOutputDTOV2 extends RepresentationModel<CidadeOutputDTOV2> {

    @ApiModelProperty(example = "1")
    private Long idCidade;

    @ApiModelProperty(example = "Parahyba")
    private String nomeCidade;

    private Long idEstado;

    private String nomeEstado;

}
