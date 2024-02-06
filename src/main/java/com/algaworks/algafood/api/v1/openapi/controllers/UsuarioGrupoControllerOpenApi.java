package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.out.GrupoOutputDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
public interface UsuarioGrupoControllerOpenApi {

    ResponseEntity<CollectionModel<GrupoOutputDTO>> listar(final Long usuarioId);

    ResponseEntity<Void> desassociar(final Long usuarioId,
                                     final Long grupoId);

    ResponseEntity<Void> associar(final Long usuarioId,
                                  final Long grupoId);

}
