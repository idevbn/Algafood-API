package com.algaworks.algafood.infraestructure.repository;

import com.algaworks.algafood.domain.repository.CustomJpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.util.Optional;

public class CustomJpaRepositoryImpl<T, ID>
        extends SimpleJpaRepository<T, ID>
        implements CustomJpaRepository<T, ID>
{
    private EntityManager manager;

    public CustomJpaRepositoryImpl(
            final JpaEntityInformation<T, ?> entityInformation,
            final EntityManager entityManager
    ) {
        super(entityInformation, entityManager);
        this.manager = entityManager;
    }

    @Override
    public Optional<T> buscarPrimeiro() {
        final String jpql = "from " + getDomainClass().getName();

        final T entity = manager.createQuery(jpql, getDomainClass())
                .setMaxResults(1)
                .getSingleResult();

        return Optional.ofNullable(entity);
    }

    @Override
    public void detach(final T entity) {
        this.manager.detach(entity);
    }

}
