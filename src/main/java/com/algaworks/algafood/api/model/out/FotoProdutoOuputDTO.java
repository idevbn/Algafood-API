package com.algaworks.algafood.api.model.out;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoOuputDTO {

    private String nomeArquivo;
    private String descricao;
    private String contentType;
    private Long tamanho;

}
