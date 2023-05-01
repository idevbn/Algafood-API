package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CatalogoFotoProdutoService {

    private final ProdutoRepository repository;

    @Autowired
    public CatalogoFotoProdutoService(final ProdutoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public FotoProduto salvar(final FotoProduto foto) {
        final FotoProduto fotoProduto = this.repository.save(foto);

        return fotoProduto;
    }

}
