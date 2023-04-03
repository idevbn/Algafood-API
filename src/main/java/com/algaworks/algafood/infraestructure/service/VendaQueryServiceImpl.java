package com.algaworks.algafood.infraestructure.service;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.List;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<VendaDiaria> consultarVendasDiarias(final VendaDiariaFilter filter) {
        final CriteriaBuilder builder = this.manager.getCriteriaBuilder();
        final CriteriaQuery<VendaDiaria> query = builder.createQuery(VendaDiaria.class);
        final Root<Pedido> root = query.from(Pedido.class);

        final Expression<LocalDate> functionDateDataCriacao = builder
                .function("date", LocalDate.class, root.get("dataCriacao"));

        final CompoundSelection<VendaDiaria> selection = builder.construct(
                VendaDiaria.class,
                functionDateDataCriacao,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal"))
        );

        query.select(selection);
        query.groupBy(functionDateDataCriacao);

        final List<VendaDiaria> vendasDiarias = this.manager
                .createQuery(query)
                .getResultList();

        return vendasDiarias;
    }

}
