package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.algaworks.algafood.domain.service.FotoStorageService.NovaFoto;

import java.io.InputStream;
import java.util.Optional;

@Service
public class CatalogoFotoProdutoService {

    private final ProdutoRepository repository;

    private final FotoStorageService fotoStorageService;

    @Autowired
    public CatalogoFotoProdutoService(final ProdutoRepository repository,
                                      final FotoStorageService fotoStorageService) {
        this.repository = repository;
        this.fotoStorageService = fotoStorageService;
    }

    @Transactional
    public FotoProduto salvar(final FotoProduto foto, final InputStream dadosArquivo) {
        final Long restauranteId = foto.getRestauranteId();

        final Long produtoId = foto.getProduto().getId();

        final String novoNomeArquivo = this.fotoStorageService
                .gerarNomeArquivo(foto.getNomeArquivo());

        final Optional<FotoProduto> fotoProdutoOpt = this.repository
                .findFotoById(restauranteId, produtoId);

        fotoProdutoOpt.ifPresent(this.repository::delete);

        foto.setNomeArquivo(novoNomeArquivo);

        /*
         * Caso haja algum problema no banco de dados, será executado o rollback.
         * Isso evita que a fota seja armazenada incorretamente.
         */
        final FotoProduto fotoProduto = this.repository.save(foto);
        this.repository.flush();

        final NovaFoto novaFoto = NovaFoto.builder()
                .nomeArquivo(foto.getNomeArquivo())
                .inputStream(dadosArquivo)
                .build();

        this.fotoStorageService.armazenar(novaFoto);

        return fotoProduto;
    }

}
