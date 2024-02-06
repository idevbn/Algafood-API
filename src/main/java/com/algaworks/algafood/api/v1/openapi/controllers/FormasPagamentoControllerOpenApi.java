package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.in.FormaPagamentoInputDTO;
import com.algaworks.algafood.api.v1.model.out.FormaPagamentoOutputDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

@SecurityRequirement(name = "security_auth")
public interface FormasPagamentoControllerOpenApi {

    ResponseEntity<FormaPagamentoOutputDTO> buscar(final Long formaPagamentoId,
                                                   final ServletWebRequest request);

    ResponseEntity<FormaPagamentoOutputDTO> adicionar(
            final FormaPagamentoInputDTO formaPagamentoInputDTO
    );

    ResponseEntity<FormaPagamentoOutputDTO> atualizar(final Long formaPagamentoId,
                                                      final FormaPagamentoInputDTO formaPagamentoInputDTO);

    ResponseEntity<Void> remover(final Long formaPagamentoId);

}
