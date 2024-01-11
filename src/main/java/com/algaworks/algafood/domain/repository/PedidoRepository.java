package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>,
        JpaSpecificationExecutor<Pedido> {

    Optional<Pedido> findByCodigo(@PathVariable(value = "codigo") final String codigo);

    @Query(value = "from Pedido p join fetch p.cliente join fetch p.restaurante r join fetch r.cozinha")
    List<Pedido> findAll();

    boolean isPedidoGerenciadoPor(final String codigoPedido, final Long usuarioId);

}
