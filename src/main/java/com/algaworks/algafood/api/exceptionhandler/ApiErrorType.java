package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

/**
 * Enum que define as propriedades das mensagens de erro customizadas
 */
@Getter
public enum ApiErrorType {

    ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada");

    private String title;
    private String uri;

    ApiErrorType(final String path, final String title) {
        this.uri = "https://algafood.com.br" + path;
        this.title = title;
    }

}
