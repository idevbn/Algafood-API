package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.in.SenhaInputDTO;
import com.algaworks.algafood.api.v1.model.in.UsuarioComSenhaInputDTO;
import com.algaworks.algafood.api.v1.model.in.UsuarioInputDTO;
import com.algaworks.algafood.api.v1.model.out.UsuarioOutputDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
public interface UsuarioControllerOpenApi {

    ResponseEntity<CollectionModel<UsuarioOutputDTO>> listar();

    ResponseEntity<UsuarioOutputDTO> buscar(final Long usuarioId);

    ResponseEntity<UsuarioOutputDTO> adicionar(
            final UsuarioComSenhaInputDTO usuarioComSenhaInputDTO
    );

    ResponseEntity<UsuarioOutputDTO> atualizar(final Long usuarioId,
                                               final UsuarioInputDTO usuarioInputDTO);

    ResponseEntity<Void> alterarSenha(final Long usuarioId,
                                      final SenhaInputDTO senha);
}
