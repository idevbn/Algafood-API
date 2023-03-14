package com.algaworks.algafood.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

    public UsuarioNaoEncontradoException(final String msg) {
        super(msg);
    }

    public UsuarioNaoEncontradoException(final Long id) {
        this(String.format("Não existe um usuário cadastrado com o id=%d", id));
    }

}
