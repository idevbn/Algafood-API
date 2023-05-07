package com.algaworks.algafood.domain.exception;

public class FotoProdutoNaoEncontradaException extends EntidadeNaoEncontradaException {

    public FotoProdutoNaoEncontradaException(final String msg) {
        super(msg);
    }

    public FotoProdutoNaoEncontradaException(final Long restauranteId, final Long produtoId) {
        this(String.format("Não existe uma foto para o restaurante de id = %d e o produto de id = %d",
                restauranteId,
                produtoId)
        );
    }

}
