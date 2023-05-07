package com.algaworks.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    void armazenar(final NovaFoto novaFoto);

    void remover(final String nomeArquivo);

    InputStream recuperar(final String nomeArquivo);

    default String gerarNomeArquivo(final String nomeOriginal) {
        final String nomeNovoArquivo = UUID.randomUUID() + "_" + nomeOriginal;

        return nomeNovoArquivo;
    }

    default void substituir(final String nomeArquivoExistente,
                            final NovaFoto novaFoto) {
        this.armazenar(novaFoto);

        if (nomeArquivoExistente != null) {
            this.remover(nomeArquivoExistente);
        }
    }

    @Getter
    @Builder
    class NovaFoto {
        private String nomeArquivo;
        private InputStream inputStream;
    }

}
