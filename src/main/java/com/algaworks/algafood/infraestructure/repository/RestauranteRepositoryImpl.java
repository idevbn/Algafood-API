package com.algaworks.algafood.infraestructure.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurante> find(
            String nome,
            BigDecimal taxaFreteInicial,
            BigDecimal taxaFreteFinal
    ) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);

        criteria.from(Restaurante.class);

        TypedQuery<Restaurante> query = manager
                .createQuery(criteria);

        List<Restaurante> restauranteList = query.getResultList();

        return restauranteList;
    }
}
