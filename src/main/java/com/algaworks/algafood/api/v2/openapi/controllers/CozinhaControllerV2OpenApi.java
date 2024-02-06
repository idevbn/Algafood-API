package com.algaworks.algafood.api.v2.openapi.controllers;

import com.algaworks.algafood.api.v2.model.CozinhaOutputDTOV2;
import com.algaworks.algafood.api.v2.model.in.CozinhaInputDTOV2;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

public interface CozinhaControllerV2OpenApi {

    ResponseEntity<PagedModel<CozinhaOutputDTOV2>> listar(final Pageable pageable);

    ResponseEntity<CozinhaOutputDTOV2> buscar(final Long cozinhaId);

    ResponseEntity<CozinhaOutputDTOV2> adicionar(
            final CozinhaInputDTOV2 cozinhaInput
    );

    ResponseEntity<CozinhaOutputDTOV2> atualizar(final Long cozinhaId,
                                                 final CozinhaInputDTOV2 cozinhaInput);

    ResponseEntity<Void> remover(final Long cozinhaId);

}
