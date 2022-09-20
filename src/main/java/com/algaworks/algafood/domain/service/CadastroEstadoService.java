package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CadastroEstadoService {

    @Autowired
    private EstadoRepository repository;

    public List<Estado> listar() {
        List<Estado> estados = this.repository.findAll();

        return estados;
    }

    public Estado buscar(Long id) {
        Optional<Estado> estadoBuscado = this.repository.findById(id);

        if (estadoBuscado.isEmpty()) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um estado cadastrado com o id=%d", id)
            );
        }

        return estadoBuscado.get();
    }

    public Estado salvar(Estado estado) {
        Estado estadoSalvo = this.repository.save(estado);

        return estadoSalvo;
    }

    public void excluir(Long id) {
        try {
            this.repository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um cadastro de estado com id=%d", id)
            );
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException(
                    String.format("Estado de id=%d não pode ser removido pois está em uso", id)
            );
        }
    }
}
