package com.algaworks.algafood.infraestructure.repository;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CidadeRepositoryImpl implements CidadeRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Cidade> listar() {
        TypedQuery<Cidade> query = manager.createQuery("from Cidade", Cidade.class);

        return query.getResultList();
    }

    @Override
    public Cidade buscar(Long id) {
        Cidade cidade = manager.find(Cidade.class, id);

        return cidade;
    }

    @Transactional
    @Override
    public Cidade salvar(Cidade cidade) {
        Cidade cidadePersistida = manager.merge(cidade);

        return cidadePersistida;
    }

    @Transactional
    @Override
    public void remover(Long id) {
        Cidade cidade = this.buscar(id);

        if (cidade == null) {
            throw new EmptyResultDataAccessException(1);
        }

        manager.remove(cidade);
    }
}
