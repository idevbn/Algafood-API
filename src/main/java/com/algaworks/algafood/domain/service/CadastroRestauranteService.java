package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CadastroRestauranteService {

    private final RestauranteRepository repository;
    private final CadastroCozinhaService cozinhaService;
    private final CadastroCidadeService cidadeService;
    private final CadastroFormaPagamentoService formaPagamentoService;

    @Autowired
    public CadastroRestauranteService(final RestauranteRepository repository,
                                      final CadastroCozinhaService cozinhaService,
                                      final CadastroCidadeService cidadeService,
                                      final CadastroFormaPagamentoService formaPagamentoService) {
        this.repository = repository;
        this.cozinhaService = cozinhaService;
        this.cidadeService = cidadeService;
        this.formaPagamentoService = formaPagamentoService;
    }

    @Transactional
    public Restaurante salvar(final Restaurante restaurante) {
        final Long cozinhaId = restaurante.getCozinha().getId();
        final Long cidadeId = restaurante.getEndereco().getCidade().getId();

        final Cozinha cozinha = this.cozinhaService.buscarOuFalhar(cozinhaId);

        final Cidade cidade = this.cidadeService.buscarOuFalhar(cidadeId);

        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);

        final Restaurante restauranteSalvo = this.repository.save(restaurante);

        return restauranteSalvo;
    }

    public Restaurante buscarOuFalhar(final Long id) {
        final Restaurante restauranteEncontrado = this.repository.findById(id).orElseThrow(
                () -> new RestauranteNaoEncontradoException(id)
        );

        return restauranteEncontrado;
    }

    @Transactional
    public void ativar(final Long id) {
        final Restaurante restauranteAtual = this.buscarOuFalhar(id);

        restauranteAtual.ativar();
    }

    @Transactional
    public void inativar(final Long id) {
        final Restaurante restauranteAtual = this.buscarOuFalhar(id);

        restauranteAtual.inativar();
    }

    /**
     * Método que desvincula uma {@link FormaPagamento}
     * a um {@link Restaurante}, recebendo
     * @param restauranteId
     * @param formaPagamentoId
     *
     * OBS: não é necessário chamar o método 'save' para
     * salvar o {@link Restaurante}. Quando a transação
     * for finalizada, o JPA irá sincronizar o objeto res-
     * taurante com o banco de dados e durante essa sincro-
     * nização, o JPA interpretará que foi feita uma desas-
     * sociação de uma forma de pagamento ao resturante, fa-
     * zendo um INSERT na tabela tb_restaurante_forma_pagamento.
     */
    @Transactional
    public void desassociarFormaPagamento(final Long restauranteId,
                                          final Long formaPagamentoId) {
        final Restaurante restauranteEncontrado = this.buscarOuFalhar(restauranteId);

        final FormaPagamento formaPagamentoEncontrada = this.formaPagamentoService
                .buscarOuFalhar(formaPagamentoId);

        restauranteEncontrado.removerFormaPagamento(formaPagamentoEncontrada);
    }

    @Transactional
    public void associarFormaPagamento(final Long restauranteId,
                                       final Long formaPagamentoId) {
        final Restaurante restauranteEncontrado = this.buscarOuFalhar(restauranteId);

        final FormaPagamento formaPagamentoEncontrada = this.formaPagamentoService
                .buscarOuFalhar(formaPagamentoId);

        restauranteEncontrado.adicionarFormaPagamento(formaPagamentoEncontrada);
    }

}
