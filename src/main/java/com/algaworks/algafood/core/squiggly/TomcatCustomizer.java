package com.algaworks.algafood.core.squiggly;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

/**
 * Referências:
 * <li>
 *     <ul>
 *         <a href="https://stackoverflow.com/a/53613678">...</a>
 *     </ul>
 *     <ul>
 *         <a href="https://tomcat.apache.org/tomcat-8.5-doc/config/http.html">...</a>
 *     </ul>
 *     <ul>
 *         <a href="https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto-configure-webserver">...</a>
 *     </ul>
 * </li>
 */
@Component
public class TomcatCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    @Override
    public void customize(final TomcatServletWebServerFactory factory) {
        factory
                .addConnectorCustomizers(
                        connector -> connector.setAttribute("relaxedQueryChars", "[]")
                );
    }
    
}