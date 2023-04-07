package com.algaworks.algafood.infraestructure.service.report;

public class ReportException extends RuntimeException {

    public ReportException(final String message) {
        super(message);
    }

    public ReportException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
