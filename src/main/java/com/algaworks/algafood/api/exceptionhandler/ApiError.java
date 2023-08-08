package com.algaworks.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Classe que customiza o objeto de erro a ser exibido na mensagem da resposta
 */
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    @ApiModelProperty(example = "400")
    private Integer status;

    @ApiModelProperty(example = "https://algafood.com.br/dados-invalidos")
    private String type;

    @ApiModelProperty(example = "Dados inválidos")
    private String title;

    @ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
    private String detail;

    @ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
    private String userMessage;

    @ApiModelProperty(example = "2023-03-22T21:22:25.369910132Z")
    private OffsetDateTime timestamp;

    @ApiModelProperty("Lista de bjetos ou campos que geraram o erro (opcional)")
    private List<Object> objects;

    @Getter
    @Builder
    @ApiModel("ApiError Object")
    public static class Object {

        @ApiModelProperty(example = "preço")
        private String name;

        @ApiModelProperty(example = "O preço é obrigatório")
        private String userMessage;
    }

}
