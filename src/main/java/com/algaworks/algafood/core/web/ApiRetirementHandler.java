package com.algaworks.algafood.core.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Classe que adiciona o status http 410 informando a versão desligada/
 * descontinuada do software.
 *
 * @author Idevaldo Neto <idevbn@gmail.com>
 */
@Component
public class ApiRetirementHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        if (request.getRequestURI().startsWith("/v1/")) {
            response.setStatus(HttpStatus.GONE.value());
            return false;
        }

        return true;
    }

}
