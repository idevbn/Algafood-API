package com.algaworks.algafood.api.v2.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@ApiModel("CozinhaModel")
@Relation(collectionRelation = "cozinhas")
public class CozinhaOutputDTOV2 extends RepresentationModel<CozinhaOutputDTOV2> {

    @ApiModelProperty(example = "1")
    private Long idCozinha;

    @ApiModelProperty(example = "Indiana")
    private String nomeCozinha;

}
