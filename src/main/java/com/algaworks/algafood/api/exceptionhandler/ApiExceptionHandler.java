package com.algaworks.algafood.api.exceptionhandler;

import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe responsável por capturar as exceções de todos os controladores
 * <br>
 * Ver mais sobre a especificação RFC-7807 em:
 * <a href="https://www.rfc-editor.org/rfc/rfc7807">...</a>}
 */
@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro inesperado no "
            + "sistema. Tente novamente e se o problema persistir, entre em contato com o "
            + "administrador do sistema.";

    private final MessageSource messageSource;

    @Autowired
    public ApiExceptionHandler(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

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

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<?> handleValidacaoException(
            final ValidacaoException ex,
            final WebRequest request
    ) {

        final ResponseEntity<Object> response = this.handleValidationInternal(
                ex,
                ex.getBindingResult(),
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

        log.error(ex.getMessage(), ex);

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

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(
            final AccessDeniedException ex,
            final WebRequest request
    ) {
        final HttpStatus status = HttpStatus.FORBIDDEN;
        final ApiErrorType acessoNegado = ApiErrorType.ACESSO_NEGADO;
        final String detail = "Você não possui permissão para executar essa operação.";

        final ApiError apiError = this.createApiErrorBuilder(status, acessoNegado, detail)
                .userMessage(detail)
                .build();

        final ResponseEntity<Object> response = handleExceptionInternal(
                ex,
                apiError,
                new HttpHeaders(),
                HttpStatus.FORBIDDEN,
                request
        );

        return response;
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            final HttpMessageNotReadableException ex,
            final HttpHeaders headers,
            final HttpStatusCode statusCode,
            final WebRequest request
    ) {
        final Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            final ResponseEntity<Object> response = this.handleInvalidFormatException(
                    (InvalidFormatException) rootCause, headers, statusCode, request
            );

            return response;
        } else if (rootCause instanceof PropertyBindingException) {
            final ResponseEntity<Object> response = this.handlePropertyBindingException(
                    (PropertyBindingException) rootCause, headers, statusCode, request
            );

            return response;
        }

        final ApiErrorType mensagemIncompreensivel = ApiErrorType.MENSAGEM_INCOMPREENSIVEL;
        final String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";

        final ApiError apiError = this.createApiErrorBuilder(statusCode, mensagemIncompreensivel, detail)
                .build();

        final ResponseEntity<Object> response = this
                .handleExceptionInternal(ex, apiError, new HttpHeaders(), statusCode, request);

        return response;
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            final TypeMismatchException ex,
            final HttpHeaders headers,
            final HttpStatusCode statusCode,
            final WebRequest request
    ) {

        if (ex instanceof MethodArgumentTypeMismatchException) {
            final ResponseEntity<Object> response = this.handleMethodArgumentTypeMismatchException(
                    (MethodArgumentTypeMismatchException) ex, headers, statusCode, request
            );

            return response;
        }

        return super.handleTypeMismatch(ex, headers, statusCode, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            final NoHandlerFoundException ex,
            final HttpHeaders headers,
            final HttpStatusCode statusCode,
            final WebRequest request
    ) {
        final ApiErrorType recursoNaoEncontrado = ApiErrorType.RECURSO_NAO_ENCONTRADO;

        final String detail = String.format(
                "O recurso '%s' que você tentou acessar é inexistente.",
                ex.getRequestURL()
        );

        final ApiError apiError = this.createApiErrorBuilder(statusCode, recursoNaoEncontrado, detail)
                .userMessage(detail)
                .build();

        final ResponseEntity<Object> response = this
                .handleExceptionInternal(ex, apiError, headers, statusCode, request);

        return response;
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            final Exception ex,
            Object body,
            final HttpHeaders headers,
            final HttpStatusCode statusCode,
            final WebRequest request
    ) {
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (body == null) {
            body = ApiError.builder()
                    .title(HttpStatus.valueOf(statusCode.value()).getReasonPhrase())
                    .status(statusCode.value())
                    .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                    .build();
        } else if (body instanceof String) {
            body = ApiError.builder()
                    .title((String) body)
                    .status(statusCode.value())
                    .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex,
            final HttpHeaders headers,
            final HttpStatusCode statusCode,
            final WebRequest request
    ) {
        final ResponseEntity<Object> response = this.handleValidationInternal(
                ex,
                ex.getBindingResult(),
                headers,
                statusCode,
                request
        );

        return response;
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
            final HttpMediaTypeNotAcceptableException ex,
            final HttpHeaders headers,
            final HttpStatusCode statusCode,
            final WebRequest request) {

        final ResponseEntity<Object> response = ResponseEntity
                .status(statusCode)
                .headers(headers)
                .build();

        return response;
    }

    /**
     * Método privado que cria uma instância de {@link ApiError.ApiErrorBuilder}
     *
     * @param statusCode - o código HTTP da mensagem de erro
     * @param apiErrorType - instância do enum com propriedades do erro
     * @param detail - texto com a mensagem detalhada do erro
     * @return
     */
    private ApiError.ApiErrorBuilder createApiErrorBuilder(
            final HttpStatusCode statusCode, final ApiErrorType apiErrorType, final String detail
    ) {
        final ApiError.ApiErrorBuilder apiErrorBuilder = ApiError.builder()
                .status(statusCode.value())
                .type(apiErrorType.getUri())
                .title(apiErrorType.getTitle())
                .detail(detail)
                .timestamp(OffsetDateTime.now());

        return apiErrorBuilder;
    }

    private ResponseEntity<Object> handleInvalidFormatException(
            final InvalidFormatException ex,
            final HttpHeaders headers,
            final HttpStatusCode statusCode,
            final WebRequest request
    ) {
        final String path = this.joinPath(ex.getPath());

        final ApiErrorType mensagemIncompreensivel = ApiErrorType.MENSAGEM_INCOMPREENSIVEL;

        final String detail = String.format(
                MSG_ERRO_GENERICA_USUARIO_FINAL,
                path, ex.getValue(), ex.getTargetType().getSimpleName()
        );

        final ApiError apiError = this
                .createApiErrorBuilder(statusCode, mensagemIncompreensivel, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        final ResponseEntity<Object> response = this
                .handleExceptionInternal(ex, apiError, headers, statusCode, request);

        return response;
    }

    private ResponseEntity<Object> handlePropertyBindingException(
            final PropertyBindingException ex,
            final HttpHeaders headers,
            final HttpStatusCode statusCode,
            final WebRequest request
    ) {
        final String path = this.joinPath(ex.getPath());

        final ApiErrorType mensagemIncompreensivel = ApiErrorType.MENSAGEM_INCOMPREENSIVEL;

        final String detail = String.format(
                "A propriedade '%s' não existe. Corrija ou remova essa propriedade e tente novamente",
                path
        );

        final ApiError apiError = this
                .createApiErrorBuilder(statusCode, mensagemIncompreensivel, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        final ResponseEntity<Object> response = this
                .handleExceptionInternal(ex, apiError, headers, statusCode, request);

        return response;
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(
            final MethodArgumentTypeMismatchException ex,
            final HttpHeaders headers,
            final HttpStatusCode statusCode,
            final WebRequest request
    ) {
        final ApiErrorType parametroInvalido = ApiErrorType.PARAMETRO_INVALIDO;

        final String detail = String.format(
                "O parâmetro da URL '%s' recebeu um valor '%s' que é de um tipo inválido. "
                        + "Corrija e informe um valor válido com o tipo '%s'.",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()
        );

        final ApiError apiError = this
                .createApiErrorBuilder(statusCode, parametroInvalido, detail)
                .userMessage(detail)
                .build();

        final ResponseEntity<Object> response = this
                .handleExceptionInternal(ex, apiError, headers, statusCode, request);

        return response;
    }

    private ResponseEntity<Object> handleValidationInternal(
            final Exception ex,
            final BindingResult bindingResult,
            final HttpHeaders headers,
            final HttpStatusCode statusCode,
            final WebRequest request
    ) {

        final ApiErrorType errorType = ApiErrorType.DADOS_INVALIDOS;

        final String detail = "Um ou mais campos estão inválidos. " +
                "Faça o preenchimento correto e tente novamente.";

        final List<ApiError.Object> errObjects = bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    final String message = messageSource
                            .getMessage(objectError, LocaleContextHolder.getLocale());

                    String name = objectError.getObjectName();

                    if (objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField();
                    }

                    return ApiError.Object.builder()
                            .name(name)
                            .userMessage(message)
                            .build();
                })
                .collect(Collectors.toList());

        final ApiError apiError = this.createApiErrorBuilder(statusCode, errorType, detail)
                .userMessage(detail)
                .objects(errObjects)
                .build();

        final ResponseEntity<Object> response = this
                .handleExceptionInternal(ex, apiError, headers, statusCode, request);

        return response;
    }

    private String joinPath(final List<Reference> references) {
        final String path = references.stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));

        return path;
    }

}
