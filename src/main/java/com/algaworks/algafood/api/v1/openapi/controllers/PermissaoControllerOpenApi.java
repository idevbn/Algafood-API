package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.out.PermissaoOutputDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Permissões")
public interface PermissaoControllerOpenApi {
    @ApiOperation("Lista as permissões")
    ResponseEntity<CollectionModel<PermissaoOutputDTO>> listar();

}