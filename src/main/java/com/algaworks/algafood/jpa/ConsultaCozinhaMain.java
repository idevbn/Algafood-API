package com.algaworks.algafood.jpa;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ConsultaCozinhaMain {
    public static void main(String args[]) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CozinhaRepository cadastroCozinha = applicationContext.getBean(CozinhaRepository.class);

        List<Cozinha> cozinhas = cadastroCozinha.findAll();

        for (Cozinha cozinha : cozinhas) {
            System.out.printf("Cozinha: %s\n", cozinha.getNome());
        }
    }
}
