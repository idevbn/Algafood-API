package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CatalogoFotoProdutoService {

    private final ProdutoRepository repository;

    @Autowired
    public CatalogoFotoProdutoService(final ProdutoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public FotoProduto salvar(final FotoProduto foto) {
        final Long restauranteId = foto.getRestauranteId();

        final Long produtoId = foto.getProduto().getId();

        final Optional<FotoProduto> fotoProdutoOpt = this.repository
                .findFotoById(restauranteId, produtoId);

        fotoProdutoOpt.ifPresent(this.repository::delete);

        final FotoProduto fotoProduto = this.repository.save(foto);

        return fotoProduto;
    }

}
