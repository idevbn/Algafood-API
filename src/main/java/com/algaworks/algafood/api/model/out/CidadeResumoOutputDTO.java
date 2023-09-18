package com.algaworks.algafood.api.model.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "cidades")
public class CidadeResumoOutputDTO extends RepresentationModel<CidadeResumoOutputDTO> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Parahyba")
    private String nome;

    @ApiModelProperty(example = "PB")
    private String estado;

}
