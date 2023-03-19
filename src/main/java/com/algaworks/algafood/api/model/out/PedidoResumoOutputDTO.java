package com.algaworks.algafood.api.model.out;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoResumoOutputDTO {

    private Long id;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String status;
    private OffsetDateTime dataCriacao;
    private RestauranteResumoOutputDTO restaurante;
    private UsuarioOutputDTO cliente;

}
