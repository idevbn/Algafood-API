package com.algaworks.algafood.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {
    private static final long serialVersionUID = 1L;

    public RestauranteNaoEncontradoException(final String msg) {
        super(msg);
    }

    public RestauranteNaoEncontradoException(final Long id) {
        this(String.format("Não existe um restaurante cadastrado com o id=%d", id));
    }

}
