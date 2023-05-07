package com.algaworks.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    void armazenar(final NovaFoto novaFoto);

    default String gerarNomeArquivo(final String nomeOriginal) {
        final String nomeNovoArquivo = UUID.randomUUID() + "_" + nomeOriginal;

        return nomeNovoArquivo;
    }

    @Getter
    @Builder
    class NovaFoto {
        private String nomeArquivo;
        private InputStream inputStream;
    }

}
