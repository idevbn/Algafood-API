package com.algaworks.algafood.infraestructure.service.storage;

import com.algaworks.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class DiscoLocalFotoStorageService implements FotoStorageService {

    @Value(value = "${algafood.storage.local.diretorio-foto}")
    private Path diretorioFotos;

    @Override
    public void armazenar(final NovaFoto novaFoto) {
        try {
            final InputStream novaFotoInputStream = novaFoto.getInputStream();

            final Path arquivoPath = this.getArquivoPath(novaFoto.getNomeArquivo());

            final OutputStream outputStream = Files.newOutputStream(arquivoPath);

            FileCopyUtils.copy(novaFotoInputStream, outputStream);
        } catch (final Exception e) {
            throw new FotoStorageException("Não foi possível armazenar o arquivo.", e);
        }
    }

    @Override
    public void remover(final String nomeArquivo) {
        try {
            final Path arquivoPath = this.getArquivoPath(nomeArquivo);

            Files.deleteIfExists(arquivoPath);
        } catch (final Exception e) {
            throw new FotoStorageException("Não foi possível excluir o arquivo.", e);
        }
    }

    @Override
    public InputStream recuperar(final String nomeArquivo) {
        try {
            final Path arquivoPath = this.getArquivoPath(nomeArquivo);

            final InputStream inputStream = Files.newInputStream(arquivoPath);

            return inputStream;
        } catch (final Exception e) {
            throw new FotoStorageException("Não foi possível recuperar o arquivo.", e);
        }
    }

    private Path getArquivoPath(final String nomeArquivo) {
        final Path path = this.diretorioFotos.resolve(nomeArquivo);

        return path;
    }

}
