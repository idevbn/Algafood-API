package com.algaworks.algafood.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public ProdutoNaoEncontradoException(final String msg) {
        super(msg);
    }

    public ProdutoNaoEncontradoException(final Long id, final Long restauranteId) {
        this(String.format("Não existe um cadastro de produto com código %d para o restaurante de código %d", id, restauranteId));
    }

}
