package com.algaworks.algafood.api.openapi.controllers;

import com.algaworks.algafood.api.exceptionhandler.ApiError;
import com.algaworks.algafood.api.model.in.SenhaInputDTO;
import com.algaworks.algafood.api.model.in.UsuarioComSenhaInputDTO;
import com.algaworks.algafood.api.model.in.UsuarioInputDTO;
import com.algaworks.algafood.api.model.out.UsuarioOutputDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Usuários")
public interface UsuarioControllerOpenApi {

    @ApiOperation("Lista os usuários")
    ResponseEntity<CollectionModel<UsuarioOutputDTO>> listar();

    @ApiOperation("Busca um usuário por ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "400",
                    description = "ID do usuário inválido",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    ResponseEntity<UsuarioOutputDTO> buscar(
            @ApiParam(value = "ID do usuário", example = "1", required = true)
            final Long usuarioId
    );

    @ApiOperation("Cadastra um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado"),
    })
    ResponseEntity<UsuarioOutputDTO> adicionar(
            @ApiParam(name = "corpo", value = "Representação de um novo usuário", required = true)
            final UsuarioComSenhaInputDTO usuarioComSenhaInputDTO
    );

    @ApiOperation("Atualiza um usuário por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    ResponseEntity<UsuarioOutputDTO> atualizar(
            @ApiParam(value = "ID do usuário", example = "1", required = true)
            final Long usuarioId,

            @ApiParam(name = "corpo", value = "Representação de um usuário com os novos dados",
                    required = true)
            final UsuarioInputDTO usuarioInputDTO
    );

    @ApiOperation("Atualiza a senha de um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    ResponseEntity<Void> alterarSenha(
            @ApiParam(value = "ID do usuário", example = "1", required = true)
            final Long usuarioId,

            @ApiParam(name = "corpo", value = "Representação de uma nova senha",
                    required = true)
            final SenhaInputDTO senha
    );

}
