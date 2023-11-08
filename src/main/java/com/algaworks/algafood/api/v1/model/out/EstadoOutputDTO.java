package com.algaworks.algafood.api.v1.model.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "estados")
public class EstadoOutputDTO extends RepresentationModel<EstadoOutputDTO> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "PB")
    private String nome;

}