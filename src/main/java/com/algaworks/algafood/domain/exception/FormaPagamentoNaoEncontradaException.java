package com.algaworks.algafood.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public FormaPagamentoNaoEncontradaException(final String mensagem) {
        super(mensagem);
    }

    public FormaPagamentoNaoEncontradaException(final Long id) {
        this(String.format("Não existe um cadastro de forma de pagamento com código %d", id));
    }

}
