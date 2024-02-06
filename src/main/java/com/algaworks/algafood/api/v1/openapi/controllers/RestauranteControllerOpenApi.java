package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.in.RestauranteInputDTO;
import com.algaworks.algafood.api.v1.model.out.RestauranteApenasNomeOutputDTO;
import com.algaworks.algafood.api.v1.model.out.RestauranteBasicoOutputDTO;
import com.algaworks.algafood.api.v1.model.out.RestauranteOutputDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SecurityRequirement(name = "security_auth")
public interface RestauranteControllerOpenApi {

    ResponseEntity<CollectionModel<RestauranteBasicoOutputDTO>> listar();

//    @JsonView(RestauranteView.Resumo.class)
//    public ResponseEntity<CollectionModel<RestauranteBasicoOutputDTO>> listarResumido();

    ResponseEntity<CollectionModel<RestauranteApenasNomeOutputDTO>> listarApenasNomes();

    ResponseEntity<RestauranteOutputDTO> buscar(final Long restauranteId);

    ResponseEntity<RestauranteOutputDTO> adicionar(final RestauranteInputDTO restauranteInputDTO);

    ResponseEntity<RestauranteOutputDTO> atualizar(final Long restauranteId,
                                                   final RestauranteInputDTO restauranteInputDTO);

    ResponseEntity<Void> ativar(final Long restauranteId);

    ResponseEntity<Void> inativar(final Long restauranteId);

    ResponseEntity<Void> ativarMultiplos(final List<Long> restauranteIds);

    ResponseEntity<Void> inativarMultiplos(final List<Long> restauranteIds);

    ResponseEntity<Void> abrir(final Long restauranteId);

    ResponseEntity<Void> fechar(final Long restauranteId);

}
