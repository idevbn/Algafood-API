package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.in.EstadoInputDTO;
import com.algaworks.algafood.api.v1.model.out.EstadoOutputDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
public interface EstadoControllerOpenApi {

    ResponseEntity<CollectionModel<EstadoOutputDTO>> listar();

    ResponseEntity<EstadoOutputDTO> buscar(final Long estadoId);

    ResponseEntity<EstadoOutputDTO> adicionar(final EstadoInputDTO estadoInputDTO);

    ResponseEntity<EstadoOutputDTO> atualizar(final Long estadoId,
                                              final EstadoInputDTO estadoInputDTO);
    ResponseEntity<Void> remover(final Long estadoId);

}
