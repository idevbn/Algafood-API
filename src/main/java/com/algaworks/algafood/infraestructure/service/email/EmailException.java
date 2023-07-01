package com.algaworks.algafood.infraestructure.service.email;

public class EmailException extends RuntimeException {

    public EmailException(final String message) {
        super(message);
    }

    public EmailException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
