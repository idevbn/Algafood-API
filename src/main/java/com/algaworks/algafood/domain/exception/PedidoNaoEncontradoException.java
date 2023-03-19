package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public PedidoNaoEncontradoException(final String msg) {
        super(msg);
    }

    public PedidoNaoEncontradoException(final Long id) {
        this(String.format("Não foi encontrado um pedido com o id = %d", id));
    }

}
