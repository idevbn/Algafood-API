package com.algaworks.algafood.infraestructure.repository;

import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class PermissaoRepositoryImpl implements PermissaoRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Permissao> listar() {
        TypedQuery<Permissao> query = manager.createQuery("from Permissao", Permissao.class);

        return query.getResultList();
    }

    @Override
    public Permissao buscar(Long id) {
        Permissao permissao = manager.find(Permissao.class, id);

        return permissao;
    }

    @Transactional
    @Override
    public Permissao salvar(Permissao permissao) {
        Permissao permissaoPersistida = manager.merge(permissao);

        return permissaoPersistida;
    }

    @Transactional
    @Override
    public void remover(Permissao permissao) {
        permissao = buscar(permissao.getId());

        manager.remove(permissao);
    }
}
