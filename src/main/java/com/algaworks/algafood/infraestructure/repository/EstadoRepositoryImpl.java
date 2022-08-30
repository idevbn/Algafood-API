package com.algaworks.algafood.infraestructure.repository;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class EstadoRepositoryImpl implements EstadoRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Estado> listar() {
        TypedQuery<Estado> query = manager.createQuery("from Estado", Estado.class);

        return query.getResultList();
    }

    @Override
    public Estado buscar(Long id) {
        Estado estado = manager.find(Estado.class, id);

        return estado;
    }

    @Transactional
    @Override
    public Estado salvar(Estado estado) {
        Estado estadoPersistido = manager.merge(estado);

        return estadoPersistido;
    }

    @Transactional
    @Override
    public void remover(Estado estado) {
        estado = buscar(estado.getId());

        manager.remove(estado);
    }
}
