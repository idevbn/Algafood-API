package com.algaworks.algafood.infraestructure.service.storage;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class S3FotoStorageService implements FotoStorageService {

    private final AmazonS3 amazonS3;
    private final StorageProperties storageProperties;

    @Autowired
    public S3FotoStorageService(final AmazonS3 amazonS3,
                                final StorageProperties storageProperties) {
        this.amazonS3 = amazonS3;
        this.storageProperties = storageProperties;
    }

    @Override
    public FotoRecuperada recuperar(final String nomeArquivo) {
        final String caminhoArquivo = this.getCaminhoArquivo(nomeArquivo);

        final URL url = this.amazonS3.getUrl(
                this.storageProperties.getS3().getBucket(),
                caminhoArquivo
        );

        final FotoRecuperada fotoRecuperada = FotoRecuperada.builder()
                .url(url.toString())
                .build();

        return fotoRecuperada;
    }

    @Override
    public void armazenar(final NovaFoto novaFoto) {

        try {
            final String caminhoArquivo = this.getCaminhoArquivo(novaFoto.getNomeArquivo());

            final ObjectMetadata objectMetadata = new ObjectMetadata();

            objectMetadata.setContentType(novaFoto.getContentType());
            objectMetadata.setContentLength(novaFoto.getSize());

            final PutObjectRequest putObjectRequest = new PutObjectRequest(
                    this.storageProperties.getS3().getBucket(),
                    caminhoArquivo,
                    novaFoto.getInputStream(),
                    objectMetadata
            ).withCannedAcl(CannedAccessControlList.PublicRead);

            this.amazonS3.putObject(putObjectRequest);
        } catch (final Exception ex) {
            throw new FotoStorageException("Não foi possível enviar arquivo para a Amazon S3", ex);
        }
    }

    @Override
    public void remover(final String nomeArquivo) {

        try {
            final String caminhoArquivo = this.getCaminhoArquivo(nomeArquivo);

            final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(
                    this.storageProperties.getS3().getBucket(),
                    caminhoArquivo
            );

            this.amazonS3.deleteObject(deleteObjectRequest);
        } catch (final Exception ex) {
            throw new FotoStorageException("Não foi possível excluir arquivo na Amazon S3", ex);
        }
    }

    private String getCaminhoArquivo(final String nomeArquivo) {

        final String caminhoArquivo = String
                .format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);

        return caminhoArquivo;
    }

}
