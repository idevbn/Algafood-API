package com.algaworks.algafood.infraestructure.repository;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepositoryQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;


    @Override
    @Transactional
    public FotoProduto save(final FotoProduto foto) {
        final FotoProduto fotoProduto = this.manager.merge(foto);

        return fotoProduto;
    }
}