package com.algaworks.algafood.domain.exception;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {
    private static final long serialVersionUID = 1L;

    public EstadoNaoEncontradoException(final String msg) {
        super(msg);
    }

    public EstadoNaoEncontradoException(final Long id) {
        this(String.format("Não existe um cadastro de estado com id=%d", id));
    }

}
