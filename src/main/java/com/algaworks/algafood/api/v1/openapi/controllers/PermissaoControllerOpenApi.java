package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.out.PermissaoOutputDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

public interface PermissaoControllerOpenApi {

    ResponseEntity<CollectionModel<PermissaoOutputDTO>> listar();

}