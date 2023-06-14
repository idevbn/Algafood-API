package com.algaworks.algafood.infraestructure.service.storage;

import com.algaworks.algafood.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class S3FotoStorageService implements FotoStorageService {

    private final AmazonS3 amazonS3;

    @Autowired
    public S3FotoStorageService(final AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

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
