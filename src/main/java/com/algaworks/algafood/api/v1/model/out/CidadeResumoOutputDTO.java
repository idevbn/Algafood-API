package com.algaworks.algafood.api.v1.model.out;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "cidades")
public class CidadeResumoOutputDTO extends RepresentationModel<CidadeResumoOutputDTO> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Parahyba")
    private String nome;

    @Schema(example = "PB")
    private String estado;

}
