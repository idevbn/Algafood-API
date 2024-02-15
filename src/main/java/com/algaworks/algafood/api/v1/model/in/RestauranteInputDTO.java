package com.algaworks.algafood.api.v1.model.in;

import com.algaworks.algafood.core.validation.TaxaFrete;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteInputDTO {

    @NotBlank
    @Schema(example = "Thai Gourmet")
    private String nome;

    @NotNull
    @TaxaFrete
    @Schema(example = "12.00")
    private BigDecimal taxaFrete;

    @Valid
    @NotNull
    private CozinhaIdInputDTO cozinha;

    @Valid
    @NotNull
    private EnderecoInputDTO endereco;

}
