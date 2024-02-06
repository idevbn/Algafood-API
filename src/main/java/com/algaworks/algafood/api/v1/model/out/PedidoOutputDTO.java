package com.algaworks.algafood.api.v1.model.out;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Relation(collectionRelation = "pedidos")
public class PedidoOutputDTO extends RepresentationModel<PedidoOutputDTO> {

    private String codigo;

    private BigDecimal subtotal;

    private BigDecimal taxaFrete;

    private BigDecimal valorTotal;

    private String status;

    private OffsetDateTime dataCriacao;

    private OffsetDateTime dataConfirmacao;

    private OffsetDateTime dataEntrega;

    private OffsetDateTime dataCancelamento;

    private RestauranteApenasNomeOutputDTO restaurante;

    private UsuarioOutputDTO cliente;

    private FormaPagamentoOutputDTO formaPagamento;

    private EnderecoOutputDTO enderecoEntrega;

    private List<ItemPedidoOutputDTO> itens;

}
