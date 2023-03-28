package com.algaworks.algafood.api.model.out;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@JsonFilter(value = "pedidoFilter")
public class PedidoResumoOutputDTO {

    private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String status;
    private OffsetDateTime dataCriacao;
    private RestauranteResumoOutputDTO restaurante;
    private UsuarioOutputDTO cliente;

}
