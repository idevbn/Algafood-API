package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.FotoProduto;

public interface ProdutoRepositoryQuery {

    FotoProduto save(final FotoProduto foto);

    void delete(final FotoProduto foto);

}
