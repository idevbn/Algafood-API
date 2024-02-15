package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class CadastroFormaPagamentoService {

    private static final String MSG_FORMA_PAGAMENTO_EM_USO
            = "Forma de pagamento de código %d não pode ser removida, pois está em uso";

    private final FormaPagamentoRepository repository;

    @Autowired
    public CadastroFormaPagamentoService(final FormaPagamentoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public FormaPagamento salvar(final FormaPagamento formaPagamento) {
        final FormaPagamento formaPagamentoSalva = this.repository.save(formaPagamento);

        return formaPagamentoSalva;
    }

    @Transactional
    public void excluir(final Long id) {
        try {
            this.repository.deleteById(id);
            this.repository.flush();

        } catch (final EmptyResultDataAccessException e) {
            throw new FormaPagamentoNaoEncontradaException(id);

        } catch (final DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_FORMA_PAGAMENTO_EM_USO, id));
        }
    }

    public FormaPagamento buscarOuFalhar(final Long id) {
        final FormaPagamento formaPagamento = this.repository.findById(id)
                .orElseThrow(() -> new FormaPagamentoNaoEncontradaException(id));

        return formaPagamento;
    }

}
