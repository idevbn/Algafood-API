package com.algaworks.algafood.infraestructure.service.query;

import com.algaworks.algafood.domain.enums.StatusPedido;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<VendaDiaria> consultarVendasDiarias(final VendaDiariaFilter filter,
                                                    final String timeOffset) {
        final CriteriaBuilder builder = this.manager.getCriteriaBuilder();
        final CriteriaQuery<VendaDiaria> query = builder.createQuery(VendaDiaria.class);
        final Root<Pedido> root = query.from(Pedido.class);
        final List<Predicate> predicates = new ArrayList<>();

        final Expression<Date> functionConvertTzDataCriacao = builder
                .function(
                        "convert_tz",
                        Date.class,
                        root.get("dataCriacao"),
                        builder.literal("+00:00"),
                        builder.literal(timeOffset)
                );

        final Expression<Date> functionDateDataCriacao = builder
                .function("date", Date.class, functionConvertTzDataCriacao);

        final CompoundSelection<VendaDiaria> selection = builder.construct(
                VendaDiaria.class,
                functionDateDataCriacao,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal"))
        );

        if (filter.getRestauranteId() != null) {
            predicates.add(builder.equal(root.get("restaurante").get("id"), filter.getRestauranteId()));
        }

        if (filter.getDataCriacaoInicio() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"),
                    filter.getDataCriacaoInicio()));
        }

        if (filter.getDataCriacaoFim() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"),
                    filter.getDataCriacaoFim()));
        }

        predicates
                .add(root.get("status").in(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(functionDateDataCriacao);

        final List<VendaDiaria> vendasDiarias = this.manager
                .createQuery(query)
                .getResultList();

        return vendasDiarias;
    }

}
