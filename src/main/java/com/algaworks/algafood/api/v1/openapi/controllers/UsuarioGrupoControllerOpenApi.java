package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.out.GrupoOutputDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

public interface UsuarioGrupoControllerOpenApi {

    ResponseEntity<CollectionModel<GrupoOutputDTO>> listar(final Long usuarioId);

    ResponseEntity<Void> desassociar(final Long usuarioId,
                                     final Long grupoId);

    ResponseEntity<Void> associar(final Long usuarioId,
                                  final Long grupoId);

}
