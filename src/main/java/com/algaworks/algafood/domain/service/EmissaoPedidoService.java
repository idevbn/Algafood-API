package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.*;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class EmissaoPedidoService {

    private final PedidoRepository repository;
    private final CadastroCidadeService cidadeService;
    private final CadastroUsuarioService usuarioService;
    private final CadastroRestauranteService restauranteService;
    private final CadastroFormaPagamentoService formaPagamentoService;
    private final CadastroProdutoService produtoService;

    @Autowired
    public EmissaoPedidoService(final PedidoRepository repository,
                                final CadastroCidadeService cidadeService,
                                final CadastroUsuarioService usuarioService,
                                final CadastroRestauranteService restauranteService,
                                final CadastroFormaPagamentoService formaPagamentoService,
                                final CadastroProdutoService produtoService) {
        this.repository = repository;
        this.cidadeService = cidadeService;
        this.usuarioService = usuarioService;
        this.restauranteService = restauranteService;
        this.formaPagamentoService = formaPagamentoService;
        this.produtoService = produtoService;
    }

    public Pedido buscarOuFalhar(final Long id) {
        final Pedido pedido = this.repository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));

        return pedido;
    }

    @Transactional
    public Pedido emitir(final Pedido pedido) {
        this.validarPedido(pedido);
        this.validarItens(pedido);

        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calcularValorTotal();

        final Pedido pedidoSalvo = this.repository.save(pedido);

        return pedidoSalvo;
    }

    private void validarPedido(final Pedido pedido) {
        final Cidade cidade = this.cidadeService
                .buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());

        final Usuario cliente = this.usuarioService
                .buscarOuFalhar(pedido.getCliente().getId());

        final Restaurante restaurante = this.restauranteService
                .buscarOuFalhar(pedido.getRestaurante().getId());

        final FormaPagamento formaPagamento = this.formaPagamentoService
                .buscarOuFalhar(pedido.getFormaPagamento().getId());

        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);

        if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
            throw new NegocioException(
                    String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
                    formaPagamento.getDescricao())
            );
        }
    }
    private void validarItens(final Pedido pedido) {
        pedido.getItens().forEach(item -> {
            final Produto produto = this.produtoService.buscarOuFalhar(
                    pedido.getRestaurante().getId(), item.getProduto().getId()
            );

            item.setPedido(pedido);
            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
        });
    }

}
