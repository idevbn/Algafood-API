package com.algaworks.algafood.api.model.out;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoOutputDTO {

    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private CidadeResumoOutputDTO cidade;

}
