package com.algaworks.algafood.api.v1.model.out;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "restaurantes")
public class RestauranteApenasNomeOutputDTO
        extends RepresentationModel<RestauranteApenasNomeOutputDTO> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Thai Gourmet")
    private String nome;

}
