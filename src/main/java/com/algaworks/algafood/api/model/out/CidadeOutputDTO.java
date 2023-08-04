package com.algaworks.algafood.api.model.out;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "Cidade")
public class CidadeOutputDTO {

    private Long id;
    private String nome;
    private EstadoOutputDTO estado;

}
