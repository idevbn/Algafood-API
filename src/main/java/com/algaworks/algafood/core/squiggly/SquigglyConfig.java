package com.algaworks.algafood.core.squiggly;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.web.RequestSquigglyContextProvider;
import com.github.bohnman.squiggly.web.SquigglyRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * Classe de configuração do Squiggly.
 *
 * Com o Squiggly somos capazes de filtrar parâmetros da requisição de forma
 * poderosa. A utilização desse pacote veio para substituir o @JsonFilter uti-
 * lizado como exemplo para filtragens anteriormente.
 *
 * Mais sobre o <b>Squiggly</b> em:
 * <ul>
 *     <li>
 *         <a href="https://dzone.com/articles/dynamically-filter-json-with-jackson-and-squiggly">
 *              dzone-article
 *         </a>
 *     </li>
 *     <li>
 *         <a href="https://github.com/bohnman/squiggly">
 *             official-repository
 *         </a>
 *     </li>
 * </ul>
 */
@Configuration
public class SquigglyConfig {

    @Bean
    public FilterRegistrationBean<SquigglyRequestFilter> squigglyRequestFilter(
            final ObjectMapper mapper
            ) {
        Squiggly.init(mapper, new RequestSquigglyContextProvider("campos", null));

        final List<String> urlPatterns = Arrays.asList("/pedidos/*", "/restaurantes/*");

        final FilterRegistrationBean<SquigglyRequestFilter> filterRegistration =
                new FilterRegistrationBean<>();

        filterRegistration.setFilter(new SquigglyRequestFilter());
        filterRegistration.setOrder(1);
        filterRegistration.setUrlPatterns(urlPatterns);

        return filterRegistration;
    }

}
