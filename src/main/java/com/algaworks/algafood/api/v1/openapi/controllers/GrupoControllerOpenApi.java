package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.in.GrupoInputDTO;
import com.algaworks.algafood.api.v1.model.out.GrupoOutputDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

public interface GrupoControllerOpenApi {

    ResponseEntity<CollectionModel<GrupoOutputDTO>> listar();

    ResponseEntity<GrupoOutputDTO> buscar(final Long id);

    ResponseEntity<GrupoOutputDTO> adicionar(final GrupoInputDTO grupoInputDTO);

    ResponseEntity<GrupoOutputDTO> atualizar(final Long id,
                                             final GrupoInputDTO grupoInputDTO);
    ResponseEntity<Void> excluir(final Long id);

}
