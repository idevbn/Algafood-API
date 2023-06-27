package com.algaworks.algafood.core.storage;

import com.algaworks.algafood.domain.service.FotoStorageService;
import com.algaworks.algafood.infraestructure.service.storage.DiscoLocalFotoStorageService;
import com.algaworks.algafood.infraestructure.service.storage.S3FotoStorageService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    private final StorageProperties storageProperties;

    @Autowired
    public StorageConfig(final StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Bean
    public AmazonS3 amazonS3() {

        final BasicAWSCredentials awsCredentials = new BasicAWSCredentials(
                this.storageProperties.getS3().getIdChaveAcesso(),
                this.storageProperties.getS3().getChaveAcessoSecreta()
        );

        final AmazonS3 build = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(this.storageProperties.getS3().getRegiao())
                .build();

        return build;
    }

    @Bean
    public FotoStorageService fotoStorageService() {

        if (StorageProperties.TipoStorage.S3.equals(this.storageProperties.getTipo())) {
            return new S3FotoStorageService();
        } else {
            return new DiscoLocalFotoStorageService();
        }

    }

}
