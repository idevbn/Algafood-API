package com.algaworks.algafood.api.model.out;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeOutputDTO {

    private Long id;
    private String nome;
    private EstadoOutputDTO estado;

}
