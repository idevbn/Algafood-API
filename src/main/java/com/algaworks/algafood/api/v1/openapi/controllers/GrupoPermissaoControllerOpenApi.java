package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.out.PermissaoOutputDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
public interface GrupoPermissaoControllerOpenApi {

    ResponseEntity<CollectionModel<PermissaoOutputDTO>> listar(final Long grupoId);

    ResponseEntity<Void> desassociar(final Long grupoId, final Long permissaoId);

    ResponseEntity<Void> associar(final Long grupoId, final Long permissaoId);

}
