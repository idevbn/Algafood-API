package com.algaworks.algafood.domain.exception;

public class NegocioException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NegocioException(final String msg) {
        super(msg);
    }

    public NegocioException(final String msg, Throwable cause) {
        super(msg, cause);
    }

}
