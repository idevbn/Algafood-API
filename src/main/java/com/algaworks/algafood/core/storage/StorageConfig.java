package com.algaworks.algafood.core.storage;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Config {

    private final StorageProperties storageProperties;

    @Autowired
    public AmazonS3Config(final StorageProperties storageProperties) {
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

}
