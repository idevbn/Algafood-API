package com.algaworks.algafood.api.v1.model.in;

import com.algaworks.algafood.core.validation.TaxaFrete;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteInputDTO {

    @NotBlank
    private String nome;

    @NotNull
    @TaxaFrete
    private BigDecimal taxaFrete;

    @Valid
    @NotNull
    private CozinhaIdInputDTO cozinha;

    @Valid
    @NotNull
    private EnderecoInputDTO endereco;

}
