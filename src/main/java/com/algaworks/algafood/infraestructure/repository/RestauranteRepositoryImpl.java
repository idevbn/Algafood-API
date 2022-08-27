package com.algaworks.algafood.infraestructure.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurante> listar() {
        TypedQuery<Restaurante> query = manager.createQuery("from Restaurante", Restaurante.class);

        return query.getResultList();
    }

    @Override
    public Restaurante buscar(Long id) {
        Restaurante restaurante = manager.find(Restaurante.class, id);

        return restaurante;
    }

    @Override
    public Restaurante salvar(Restaurante restaurante) {
        Restaurante restaurantePersistido = manager.merge(restaurante);

        return restaurantePersistido;
    }

    @Override
    public void remover(Restaurante restaurante) {
        restaurante = buscar(restaurante.getId());

        manager.remove(restaurante);
    }
}