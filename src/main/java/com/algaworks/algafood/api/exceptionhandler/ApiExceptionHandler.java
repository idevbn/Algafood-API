package com.algaworks.algafood.api.exceptionhandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
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

    public static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro inesperado no "
            + "sistema. Tente novamente e se o problema persistir, entre em contato com o "
            + "administrador do sistema.";

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(
            final EntidadeNaoEncontradaException ex,
            final WebRequest request
    ) {
        final HttpStatus status = HttpStatus.NOT_FOUND;
        final ApiErrorType entidadeNaoEncontrada = ApiErrorType.RECURSO_NAO_ENCONTRADO;
        final String detail = ex.getMessage();

        final ApiError apiError = this.createApiErrorBuilder(status, entidadeNaoEncontrada, detail)
                .userMessage(detail)
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

        final ApiError apiError = this.createApiErrorBuilder(status, entidadeEmUso, detail)
                .userMessage(detail)
                .build();

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

        final ApiError apiError = this.createApiErrorBuilder(status, erroNegocio, detail)
                .userMessage(detail)
                .build();

        final ResponseEntity<Object> response = this.handleExceptionInternal(
                ex,
                apiError,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );

        return response;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUncaught(
            final Exception ex,
            final WebRequest request
    ) {
        final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        final ApiErrorType erroDeSistema = ApiErrorType.ERRO_DE_SISTEMA;
        final String detail = "Ocorreu um erro interno inesperado no sistema. "
                + "Tente novamente e se o problema persistir, "
                + "entre em contato com o administrador do sistema.";

        ex.printStackTrace();

        final ApiError apiError = this.createApiErrorBuilder(status, erroDeSistema, detail)
                .userMessage(detail)
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
    protected ResponseEntity<Object> handleTypeMismatch(
            final TypeMismatchException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request
    ) {

        if (ex instanceof MethodArgumentTypeMismatchException) {
            final ResponseEntity<Object> response = this.handleMethodArgumentTypeMismatchException(
                    (MethodArgumentTypeMismatchException) ex, headers, status, request
            );

            return response;
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            final NoHandlerFoundException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request
    ) {
        final ApiErrorType recursoNaoEncontrado = ApiErrorType.RECURSO_NAO_ENCONTRADO;

        final String detail = String.format(
                "O recurso '%s' que você tentou acessar é inexistente.",
                ex.getRequestURL()
        );

        final ApiError apiError = this.createApiErrorBuilder(status, recursoNaoEncontrado, detail)
                .userMessage(detail)
                .build();

        final ResponseEntity<Object> response = this
                .handleExceptionInternal(ex, apiError, headers, status, request);

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
                    .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                    .build();
        } else if (body instanceof String) {
            body = ApiError.builder()
                    .title((String) body)
                    .status(status.value())
                    .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
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
                .detail(detail)
                .timestamp(LocalDateTime.now());

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
                MSG_ERRO_GENERICA_USUARIO_FINAL,
                path, ex.getValue(), ex.getTargetType().getSimpleName()
        );

        final ApiError apiError = this
                .createApiErrorBuilder(status, mensagemIncompreensivel, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

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
                .createApiErrorBuilder(status, mensagemIncompreensivel, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        final ResponseEntity<Object> response = this
                .handleExceptionInternal(ex, apiError, headers, status, request);

        return response;
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(
            final MethodArgumentTypeMismatchException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request
    ) {
        final ApiErrorType parametroInvalido = ApiErrorType.PARAMETRO_INVALIDO;

        final String detail = String.format(
                "O parâmetro da URL '%s' recebeu um valor '%s' que é de um tipo inválido. "
                        + "Corrija e informe um valor válido com o tipo '%s'.",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()
        );

        final ApiError apiError = this
                .createApiErrorBuilder(status, parametroInvalido, detail)
                .userMessage(detail)
                .build();

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
