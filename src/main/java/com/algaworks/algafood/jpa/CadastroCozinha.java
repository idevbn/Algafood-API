package com.algaworks.algafood.jpa;

import com.algaworks.algafood.domain.model.Cozinha;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class CadastroCozinha {

    @PersistenceContext
    private EntityManager manager;

    public List<Cozinha> listar() {
        TypedQuery<Cozinha> query = manager.createQuery("from Cozinha", Cozinha.class);

        return query.getResultList();
    }

    public Cozinha buscar(Long id) {
        Cozinha cozinha = manager.find(Cozinha.class, id);

        return cozinha;
    }

    @Transactional
    public Cozinha salvar(Cozinha cozinha) {
        Cozinha cozinhaPersistida = manager.merge(cozinha);

        return cozinhaPersistida;
    }
}
