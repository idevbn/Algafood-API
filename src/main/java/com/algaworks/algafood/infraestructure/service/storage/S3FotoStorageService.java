package com.algaworks.algafood.infraestructure.service.storage;

import com.algaworks.algafood.domain.service.FotoStorageService;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class S3FotoStorageService implements FotoStorageService {

    @Override
    public InputStream recuperar(final String nomeArquivo) {
        return null;
    }

    @Override
    public void armazenar(final NovaFoto novaFoto) {

    }

    @Override
    public void remover(final String nomeArquivo) {

    }


}
