package com.algaworks.algafood.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {
    private static final long serialVersionUID = 1L;

    public CidadeNaoEncontradaException(final String msg) {
        super(msg);
    }

    public CidadeNaoEncontradaException(final Long id) {
        this(String.format("Não existe uma cidade cadastrada com o id=%d", id));
    }

}
