package com.algaworks.algafood.api.v1.model.out;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteOutputDTO extends RepresentationModel<RestauranteOutputDTO> {

//    @JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
    private Long id;

//    @JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
    private String nome;

//    @JsonView(RestauranteView.Resumo.class)
    private BigDecimal taxaFrete;

//    @JsonView(RestauranteView.Resumo.class)
    private CozinhaOutputDTO cozinha;
    private Boolean ativo;
    private Boolean aberto;
    private EnderecoOutputDTO endereco;

}
