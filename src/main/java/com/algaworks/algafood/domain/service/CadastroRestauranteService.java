package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
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

    @Autowired
    public CadastroRestauranteService(final RestauranteRepository repository,
                                      final CadastroCozinhaService cozinhaService,
                                      final CadastroCidadeService cidadeService) {
        this.repository = repository;
        this.cozinhaService = cozinhaService;
        this.cidadeService = cidadeService;
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

}
