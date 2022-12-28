package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroCidadeService {

    private static final String MSG_CIDADE_NAO_ENCONTRADA = "Não existe uma cidade cadastrada com o id=%d";
    private static final String MSG_CIDADE_EM_USO = "Cidade com id=%d não pode ser removida, pois está em uso";

    @Autowired
    private CidadeRepository repository;

    @Autowired
    private CadastroEstadoService estadoService;

    public Cidade salvar(final Cidade cidade) {
        final Long estadoId = cidade.getEstado().getId();

        final Estado estado = this.estadoService.buscarOuFalhar(estadoId);

        cidade.setEstado(estado);

        final Cidade cidadeSalva = this.repository.save(cidade);

        return cidadeSalva;
    }

    public void excluir(final Long id) {
        try {
            this.repository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntidadeNaoEncontradaException(
                    String.format(MSG_CIDADE_NAO_ENCONTRADA, id)
            );
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_CIDADE_EM_USO, id));
        }
    }

    public Cidade buscarOuFalhar(final Long id) {
        final Cidade cidadeEncontrada = this.repository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format(MSG_CIDADE_NAO_ENCONTRADA, id)
                )
        );

        return cidadeEncontrada;
    }

}
