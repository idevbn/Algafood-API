package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class CadastroProdutoService {

    private final ProdutoRepository repository;

    @Autowired
    public CadastroProdutoService(final ProdutoRepository repository) {
        this.repository = repository;
    }

    public Produto buscarOuFalhar(final Long id, final Long restauranteId) {
        final Produto produtoEncontrado = this.repository.findById(id, restauranteId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id, restauranteId));

        return produtoEncontrado;
    }

    @Transactional
    public Produto salvar(final Produto produto) {
        final Produto produtoSalvo = this.repository.save(produto);

        return produtoSalvo;
    }

}
