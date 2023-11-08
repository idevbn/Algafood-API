package com.algaworks.algafood.core.web;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Classe que adiciona na resposta um header com informações customizadas da versão
 * depreciada.
 *
 * @author Idevaldo Neto <idevbn@gmail.com>
 */
@Component
public class ApiDeprecationHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        if (request.getRequestURI().startsWith("/v1/")) {
            response.addHeader("X-AlgaFood-Deprecated",
                    "Essa versão da API está depreciada e deixará de existir a partir de 01/01/2025."
                            + "Use a versão mais atual da API.");
        }

        return true;
    }

}
