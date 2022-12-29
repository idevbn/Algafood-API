package com.algaworks.algafood.api.exceptionhandler;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Classe que customiza o objeto de erro a ser exibido na mensagem da resposta
 */
@Getter
@Builder
public class ApiError {

    private LocalDateTime dataHora;
    private String mensagem;

}
