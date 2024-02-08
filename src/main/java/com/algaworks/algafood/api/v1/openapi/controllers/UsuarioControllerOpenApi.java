package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.in.SenhaInputDTO;
import com.algaworks.algafood.api.v1.model.in.UsuarioComSenhaInputDTO;
import com.algaworks.algafood.api.v1.model.in.UsuarioInputDTO;
import com.algaworks.algafood.api.v1.model.out.UsuarioOutputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Usuários")
@SecurityRequirement(name = "security_auth")
public interface UsuarioControllerOpenApi {

    @Operation(summary = "Lista os usuários")
    ResponseEntity<CollectionModel<UsuarioOutputDTO>> listar();

    @Operation(summary = "Busca um usuário por ID", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID do usuário inválido", content = {
                    @Content(schema = @Schema(ref = "ApiError")) }),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = {
                    @Content(schema = @Schema(ref = "ApiError")) }),
    })
    ResponseEntity<UsuarioOutputDTO> buscar(
            @Parameter(description = "ID do usuário", example = "1", required = true)
            final Long usuarioId
    );

    @Operation(summary = "Cadastra um usuário", responses = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado"),
    })
    ResponseEntity<UsuarioOutputDTO> adicionar(
            @RequestBody(description = "Representação de um novo usuário", required = true)
            final UsuarioComSenhaInputDTO usuarioComSenhaInputDTO
    );

    @Operation(summary = "Atualiza um usuário por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = {
                    @Content(schema = @Schema(ref = "ApiError")) })
    })
    ResponseEntity<UsuarioOutputDTO> atualizar(
            @Parameter(description = "ID do usuário", example = "1", required = true)
            final Long usuarioId,
            @RequestBody(description = "Representação de um usuário com os novos dados", required = true)
            final UsuarioInputDTO usuarioInputDTO
    );

    @Operation(summary = "Atualiza a senha de um usuário", responses = {
            @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = {
                    @Content(schema = @Schema(ref = "ApiError")) })
    })
    ResponseEntity<Void> alterarSenha(
            @Parameter(description = "ID do usuário", example = "1", required = true)
            final Long usuarioId,
            @RequestBody(description = "Representação de uma nova senha", required = true)
            final SenhaInputDTO senha
    );

}
