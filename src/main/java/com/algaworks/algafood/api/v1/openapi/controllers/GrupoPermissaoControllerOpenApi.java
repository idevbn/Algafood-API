package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.out.PermissaoOutputDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

public interface GrupoPermissaoControllerOpenApi {

    ResponseEntity<CollectionModel<PermissaoOutputDTO>> listar(final Long grupoId);

    ResponseEntity<Void> desassociar(final Long grupoId, final Long permissaoId);

    ResponseEntity<Void> associar(final Long grupoId, final Long permissaoId);

}
