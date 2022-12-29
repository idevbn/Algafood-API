package com.algaworks.algafood.api.exceptionhandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe responsável por capturar as exceções de todos os controladores
 *
 * Ver mais sobre a especificação RFC-7807 em:
 * <a href="https://www.rfc-editor.org/rfc/rfc7807">...</a>}
 */
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(
            final EntidadeNaoEncontradaException ex,
            final WebRequest request
    ) {
        final HttpStatus status = HttpStatus.NOT_FOUND;
        final ApiErrorType entidadeNaoEncontrada = ApiErrorType.ENTIDADE_NAO_ENCONTRADA;
        final String detail = ex.getMessage();

        final ApiError apiError = this.createApiErrorBuilder(status, entidadeNaoEncontrada, detail)
                .build();

        final ResponseEntity<Object> response = this.handleExceptionInternal(
                ex,
                apiError,
                new HttpHeaders(),
                status,
                request
        );

        return response;
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUsoException(
            final EntidadeEmUsoException ex,
            final WebRequest request
    ) {
        final HttpStatus status = HttpStatus.CONFLICT;
        final ApiErrorType entidadeEmUso = ApiErrorType.ENTIDADE_EM_USO;
        final String detail = ex.getMessage();

        final ApiError apiError = this.createApiErrorBuilder(status, entidadeEmUso, detail).build();

        final ResponseEntity<Object> response = handleExceptionInternal(
                ex,
                apiError,
                new HttpHeaders(),
                HttpStatus.CONFLICT,
                request
        );

        return response;
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(
            final NegocioException ex,
            final WebRequest request
    ) {
        final HttpStatus status = HttpStatus.BAD_REQUEST;
        final ApiErrorType erroNegocio = ApiErrorType.ERRO_NEGOCIO;
        final String detail = ex.getMessage();

        final ApiError apiError = this.createApiErrorBuilder(status, erroNegocio, detail).build();

        final ResponseEntity<Object> response = this.handleExceptionInternal(
                ex,
                apiError,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );

        return response;
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            final HttpMessageNotReadableException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request
    ) {
        final Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            final ResponseEntity<Object> response = this.handleInvalidFormatException(
                    (InvalidFormatException) rootCause, headers, status, request
            );

            return response;
        } else if (rootCause instanceof PropertyBindingException) {
            final ResponseEntity<Object> response = this.handlePropertyBindingException(
                    (PropertyBindingException) rootCause, headers, status, request
            );

            return response;
        }

        final ApiErrorType mensagemIncompreensivel = ApiErrorType.MENSAGEM_INCOMPREENSIVEL;
        final String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";

        final ApiError apiError = this.createApiErrorBuilder(status, mensagemIncompreensivel, detail)
                .build();

        final ResponseEntity<Object> response = this
                .handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);

        return response;
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            final Exception ex,
            Object body,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request
    ) {
        if (body == null) {
            body = ApiError.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .build();
        } else if (body instanceof String) {
            body = ApiError.builder()
                    .title((String) body)
                    .status(status.value())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    /**
     * Método privado que cria uma instância de {@link ApiError.ApiErrorBuilder}
     *
     * @param status - o código HTTP da mensagem de erro
     * @param apiErrorType - instância do enum com propriedades do erro
     * @param detail - texto com a mensagem detalhada do erro
     * @return
     */
    private ApiError.ApiErrorBuilder createApiErrorBuilder(
            final HttpStatus status, final ApiErrorType apiErrorType, final String detail
    ) {
        final ApiError.ApiErrorBuilder apiErrorBuilder = ApiError.builder()
                .status(status.value())
                .type(apiErrorType.getUri())
                .title(apiErrorType.getTitle())
                .detail(detail);

        return apiErrorBuilder;
    }

    private ResponseEntity<Object> handleInvalidFormatException(
            final InvalidFormatException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request
    ) {
        final String path = this.joinPath(ex.getPath());

        final ApiErrorType mensagemIncompreensivel = ApiErrorType.MENSAGEM_INCOMPREENSIVEL;

        final String detail = String.format(
                "A propriedade '%s' recebeu o valor '%s'"
                        + " que é de um tipo inválido. Corrija e informe um valor compatível "
                        + "com o tipo '%s'."
                , path, ex.getValue(), ex.getTargetType().getSimpleName()
        );

        final ApiError apiError = this
                .createApiErrorBuilder(status, mensagemIncompreensivel, detail).build();

        final ResponseEntity<Object> response = this
                .handleExceptionInternal(ex, apiError, headers, status, request);

        return response;
    }

    private ResponseEntity<Object> handlePropertyBindingException(
            final PropertyBindingException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request
    ) {
        final String path = this.joinPath(ex.getPath());

        final ApiErrorType mensagemIncompreensivel = ApiErrorType.MENSAGEM_INCOMPREENSIVEL;

        final String detail = String.format(
                "A propriedade '%s' não existe. Corrija ou remova essa propriedade e tente novamente",
                path
        );

        final ApiError apiError = this
                .createApiErrorBuilder(status, mensagemIncompreensivel, detail).build();

        final ResponseEntity<Object> response = this
                .handleExceptionInternal(ex, apiError, headers, status, request);

        return response;
    }

    private String joinPath(final List<Reference> references) {
        final String path = references.stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));

        return path;
    }

}
