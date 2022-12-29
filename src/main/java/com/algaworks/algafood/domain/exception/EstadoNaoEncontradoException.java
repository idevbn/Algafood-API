package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {
    private static final long serialVersionUID = 1L;

    public EstadoNaoEncontradoException(final String msg) {
        super(msg);
    }

    public EstadoNaoEncontradoException(final Long id) {
        this(String.format("Não existe um cadastro de estado com id=%d", id));
    }

}
