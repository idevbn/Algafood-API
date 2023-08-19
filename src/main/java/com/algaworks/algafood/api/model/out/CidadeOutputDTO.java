package com.algaworks.algafood.api.model.out;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@ApiModel(value = "Cidade")
public class CidadeOutputDTO extends RepresentationModel<CidadeOutputDTO> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Parahyba")
    private String nome;

    private EstadoOutputDTO estado;

}
