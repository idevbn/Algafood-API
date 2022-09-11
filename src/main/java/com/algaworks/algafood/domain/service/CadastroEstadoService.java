package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroEstadoService {

    @Autowired
    private EstadoRepository repository;

    public List<Estado> listar() {
        List<Estado> estados = this.repository.listar();

        return estados;
    }
}
