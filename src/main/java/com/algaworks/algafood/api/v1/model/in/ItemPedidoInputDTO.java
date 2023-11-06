package com.algaworks.algafood.api.v1.model.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
public class ItemPedidoInputDTO {
    
    @NotNull
    @ApiModelProperty(example = "1", required = true)
    private Long produtoId;
    
    @NotNull
    @PositiveOrZero
    @ApiModelProperty(example = "2", required = true)
    private Integer quantidade;

    @ApiModelProperty(example = "Menos picante, por favor")
    private String observacao;

}
