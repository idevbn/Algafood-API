package com.algaworks.algafood.api.v1.model.out;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "cozinhas")
public class CozinhaOutputDTO extends RepresentationModel<CozinhaOutputDTO> {

    @Schema(example = "1")
//    @JsonView(RestauranteView.Resumo.class)
    private Long id;

    @Schema(example = "Indiana")
//    @JsonView(RestauranteView.Resumo.class)
    private String nome;

}
