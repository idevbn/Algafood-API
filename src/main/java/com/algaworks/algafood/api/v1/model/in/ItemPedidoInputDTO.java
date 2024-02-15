package com.algaworks.algafood.api.v1.model.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class ItemPedidoInputDTO {
    
    @NotNull
    @Schema(example = "1")
    private Long produtoId;
    
    @NotNull
    @Min(1)
    @Schema(example = "2")
    private Integer quantidade;

    @Schema(example = "Menos picante, por favor")
    private String observacao;

}
