package com.algaworks.algafood.jpa;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;

public class InclusaoRestauranteMain {
    public static void main(String args[]) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);

        Restaurante restaurante1 = new Restaurante();
        restaurante1.setNome("A Casa da Picanha");
        restaurante1.setTaxaFrete(BigDecimal.valueOf(10.59));

        Restaurante restaurante2 = new Restaurante();
        restaurante2.setNome("El Taco - Comida Mexicana");
        restaurante2.setTaxaFrete(BigDecimal.valueOf(13.67));

        restaurante1 = restauranteRepository.salvar(restaurante1);
        restaurante2 = restauranteRepository.salvar(restaurante2);

        System.out.printf("%d - %s - %f\n",
                restaurante1.getId(),
                restaurante1.getNome(),
                restaurante1.getTaxaFrete()
        );
        System.out.printf("%d - %s - %f\n",
                restaurante2.getId(),
                restaurante2.getNome(),
                restaurante2.getTaxaFrete()
        );
    }
}
