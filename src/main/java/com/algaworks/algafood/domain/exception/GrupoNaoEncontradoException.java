package com.algaworks.algafood.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public GrupoNaoEncontradoException(final String msg) {
        super(msg);
    }

    public GrupoNaoEncontradoException(final Long id) {
        this(String.format("Não existe um cadastro de grupo com id %d", id));
    }

}
