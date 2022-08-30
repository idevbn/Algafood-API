package com.algaworks.algafood.infraestructure.repository;

import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<FormaPagamento> listar() {
        TypedQuery<FormaPagamento> query = manager.createQuery("from FormaPagamento", FormaPagamento.class);

        return query.getResultList();
    }

    @Override
    public FormaPagamento buscar(Long id) {
        FormaPagamento formaPagamento = manager.find(FormaPagamento.class, id);

        return formaPagamento;
    }

    @Transactional
    @Override
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        FormaPagamento formaPagamentoPersistida = manager.merge(formaPagamento);

        return formaPagamentoPersistida;
    }

    @Transactional
    @Override
    public void remover(FormaPagamento formaPagamento) {
        formaPagamento = buscar(formaPagamento.getId());

        manager.remove(formaPagamento);
    }
}
