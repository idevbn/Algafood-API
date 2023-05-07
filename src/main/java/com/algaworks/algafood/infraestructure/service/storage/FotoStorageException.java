package com.algaworks.algafood.infraestructure.service.storage;

public class FotoStorageException extends RuntimeException {

    public FotoStorageException(final String message) {
        super(message);
    }

    public FotoStorageException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
