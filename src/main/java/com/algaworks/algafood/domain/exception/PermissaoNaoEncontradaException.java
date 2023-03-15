package com.algaworks.algafood.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

    public PermissaoNaoEncontradaException(final String msg) {
        super(msg);
    }

    public PermissaoNaoEncontradaException(final Long id) {
        this(String.format("Não existe um cadastro de permissão com código %d", id));
    }

}
