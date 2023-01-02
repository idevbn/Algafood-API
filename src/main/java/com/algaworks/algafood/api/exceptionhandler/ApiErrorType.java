package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

/**
 * Enum que define as propriedades das mensagens de erro customizadas
 */
@Getter
public enum ApiErrorType {

    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido");

    private String title;
    private String uri;

    ApiErrorType(final String path, final String title) {
        this.uri = "https://algafood.com.br" + path;
        this.title = title;
    }

}
