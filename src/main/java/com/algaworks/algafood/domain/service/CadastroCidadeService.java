package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroCidadeService {

    @Autowired
    private CidadeRepository repository;

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Cidade> listar() {
        List<Cidade> cidades = this.repository.listar();

        return cidades;
    }

    public Cidade buscar(Long id) {
        Cidade cidadeBuscada = this.repository.buscar(id);

        if (cidadeBuscada == null) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe uma cidade cadastrada com o id=%d", id)
            );
        }

        Long estadoId = cidadeBuscada.getEstado().getId();
        Estado estadoBuscado = this.estadoRepository.buscar(estadoId);

        if (estadoBuscado == null) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um estado cadastrado com o id=%d", id)
            );
        }

        return cidadeBuscada;
    }

    public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Estado estado = this.estadoRepository.buscar(estadoId);

        if (estado == null) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um estado cadastrado com o id=%d", estadoId)
            );
        }

        cidade.setEstado(estado);

        Cidade cidadeSalva = this.repository.salvar(cidade);

        return cidadeSalva;
    }

    public void excluir(Long id) {
        try {
            this.repository.remover(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um cadastro de cidade com id=%d", id)
            );
        } 
    }
}
