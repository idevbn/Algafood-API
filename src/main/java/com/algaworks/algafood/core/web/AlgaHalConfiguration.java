package com.algaworks.algafood.core.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.mediatype.hal.HalConfiguration;
import org.springframework.http.MediaType;

/**
 * Classe de configuração do Hal responsável pela determinação
 * dos tipos de mídia utilizados pela aplicação.
 * <br>
 * Mais em:
 * <br>
 * <li>
 *     <ul>
 *          <a href="https://www.baeldung.com/spring-rest-hal">...</a>
 *     </ul>
 *     <ul>
 *          <a href="https://docs.spring.io/spring-hateoas/docs/current/reference/html/#:~:text=HAL%20%E2%80%93%20Hypertext%20Application%20Language">...</a>
 *     </ul>
 *  </li>
 *
 * @author Idevaldo Neto <idevbn@gmail.com>
 */
@Configuration
public class AlgaHalConfiguration {

    @Bean
    public HalConfiguration globalPolicy() {
        return new HalConfiguration()
                .withMediaType(MediaType.APPLICATION_JSON)
                .withMediaType(AlgaMediaTypes.V1_APPLICATION_JSON);
    }

}
