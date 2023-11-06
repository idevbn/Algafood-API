package com.algaworks.algafood.api.v1.model.out;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@ApiModel(value = "Cidade")
@Relation(collectionRelation = "cidades")
public class CidadeOutputDTO extends RepresentationModel<CidadeOutputDTO> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Parahyba")
    private String nome;

    private EstadoOutputDTO estado;

}
