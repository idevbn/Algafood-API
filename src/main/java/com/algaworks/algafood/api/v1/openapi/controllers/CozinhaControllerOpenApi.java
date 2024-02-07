package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.in.CozinhaInputDTO;
import com.algaworks.algafood.api.v1.model.out.CozinhaOutputDTO;
import com.algaworks.algafood.core.springdoc.PageableParameter;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
public interface CozinhaControllerOpenApi {

    @PageableParameter
    ResponseEntity<PagedModel<CozinhaOutputDTO>> listar(
            @Parameter(hidden = true) final Pageable pageable
    );

    ResponseEntity<CozinhaOutputDTO> buscar(final Long cozinhaId);

    ResponseEntity<CozinhaOutputDTO> adicionar(final CozinhaInputDTO cozinhaInput);

    ResponseEntity<CozinhaOutputDTO> atualizar(final Long cozinhaId,
                                               final CozinhaInputDTO cozinhaInput);

    ResponseEntity<Void> remover(final Long cozinhaId);

}
