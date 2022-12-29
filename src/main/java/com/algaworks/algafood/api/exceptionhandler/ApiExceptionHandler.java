package com.algaworks.algafood.api.exceptionhandler;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/**
 * Classe responsável por capturar as exceções de todos os controladores
 */
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(
            final EntidadeNaoEncontradaException e
    ) {
        final ApiError apiError = ApiError.builder()
                .dataHora(LocalDateTime.now())
                .mensagem(e.getMessage())
                .build();

        final ResponseEntity<ApiError> response = ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(apiError);

        return response;
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(final NegocioException e) {
        final ApiError apiError = ApiError.builder()
                .dataHora(LocalDateTime.now())
                .mensagem(e.getMessage())
                .build();

        final ResponseEntity<ApiError> response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(apiError);

        return response;
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<?> handleHttpMediaTypeNotSupportedException(
            final HttpMediaTypeNotSupportedException e
    ) {
        final ApiError apiError = ApiError.builder()
                .dataHora(LocalDateTime.now())
                .mensagem("O tipo de mídia não é aceito.")
                .build();

        final ResponseEntity<ApiError> response = ResponseEntity
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(apiError);

        return response;
    }

}
