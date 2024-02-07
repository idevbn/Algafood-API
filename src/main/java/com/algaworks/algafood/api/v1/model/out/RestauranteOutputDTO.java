package com.algaworks.algafood.api.v1.model.out;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteOutputDTO extends RepresentationModel<RestauranteOutputDTO> {

    @Schema(example = "1")
//    @JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
    private Long id;

    @Schema(example = "Thai Gourmet")
//    @JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
    private String nome;

    @Schema(example = "12")
//    @JsonView(RestauranteView.Resumo.class)
    private BigDecimal taxaFrete;

//    @JsonView(RestauranteView.Resumo.class)
    private CozinhaOutputDTO cozinha;
    private Boolean ativo;
    private Boolean aberto;
    private EnderecoOutputDTO endereco;

}
