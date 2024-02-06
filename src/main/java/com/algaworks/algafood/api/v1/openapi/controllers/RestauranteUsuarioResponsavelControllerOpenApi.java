package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.out.UsuarioOutputDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
public interface RestauranteUsuarioResponsavelControllerOpenApi {

    ResponseEntity<CollectionModel<UsuarioOutputDTO>> listar(final Long restauranteId);

    ResponseEntity<Void> desassociar(final Long restauranteId,
                                     final Long usuarioId);

    ResponseEntity<Void> associar(final Long restauranteId,
                                  final Long usuarioId);

}
