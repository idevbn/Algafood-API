package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CadastroCidadeService {

    @Autowired
    private CidadeRepository repository;

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Cidade> listar() {
        List<Cidade> cidades = this.repository.findAll();

        return cidades;
    }

    public Cidade buscar(Long id) {
        Optional<Cidade> cidadeBuscada = this.repository.findById(id);

        if (cidadeBuscada.isEmpty()) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe uma cidade cadastrada com o id=%d", id)
            );
        }

        Long estadoId = cidadeBuscada.get().getEstado().getId();
        Optional<Estado> estadoBuscado = this.estadoRepository.findById(estadoId);

        if (estadoBuscado.isEmpty()) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um estado cadastrado com o id=%d", id)
            );
        }

        return cidadeBuscada.get();
    }

    public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Optional<Estado> estado = this.estadoRepository.findById(estadoId);

        if (estado.isEmpty()) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um estado cadastrado com o id=%d", estadoId)
            );
        }

        cidade.setEstado(estado.get());

        Cidade cidadeSalva = this.repository.save(cidade);

        return cidadeSalva;
    }

    public void excluir(Long id) {
        try {
            this.repository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um cadastro de cidade com id=%d", id)
            );
        }
    }
}
