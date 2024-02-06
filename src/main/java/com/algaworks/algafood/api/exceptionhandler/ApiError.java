package com.algaworks.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Classe que customiza o objeto de erro a ser exibido na mensagem da resposta
 */
@Getter
@Builder
@Schema(name = "ApiError")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    @Schema(example = "400")
    private Integer status;

    @Schema(example = "https://algafood.com.br/dados-invalidos")
    private String type;

    @Schema(example = "Dados inválidos")
    private String title;

    @Schema(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
    private String detail;

    @Schema(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
    private String userMessage;

    @Schema(example = "2024-02-06T19:42:10.902245498Z")
    private OffsetDateTime timestamp;

    @Schema(example = "Lista de objetos ou campos que geraram o erro")
    private List<Object> objects;

    @Getter
    @Builder
    @Schema(name = "ObjectApiError")
    public static class Object {

        @Schema(name = "preco")
        private String name;

        @Schema(name = "O preço é inválido")
        private String userMessage;
    }

}
