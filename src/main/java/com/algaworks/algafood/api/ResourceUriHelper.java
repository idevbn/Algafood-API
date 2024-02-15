package com.algaworks.algafood.api;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.servlet.http.HttpServletResponse;
import java.net.URI;

/**
 * Essa classe tem como responsabilidade, adicionar ao header da requisição
 * de criação de recursos, usando o verbo Http POST, o cabeçalho LOCATION.
 * Este cabeçalho auxilia no fluxo das requisições pois, após a criação do
 * recurso é possível copiar o link com a uri que o recupera, no header da
 * resposta.
 * <br>
 * Obserção: o header poderia ser adicionado diretamente no ResponseEntity.
 */
@UtilityClass
public class ResourceUriHelper {

    public static void addUriInReponseHeader(final Object resourceId) {

        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(resourceId)
                .toUri();

        final HttpServletResponse servletResponse = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes())
                .getResponse();

        servletResponse.setHeader(HttpHeaders.LOCATION, uri.toString());

    }

}
