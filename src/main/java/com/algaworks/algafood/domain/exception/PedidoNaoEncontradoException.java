package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public PedidoNaoEncontradoException(final String codigo) {
        super(String.format("Não foi encontrado um pedido com o código = %s", codigo));
    }

}
