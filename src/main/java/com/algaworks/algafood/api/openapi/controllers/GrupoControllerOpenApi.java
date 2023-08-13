package com.algaworks.algafood.api.openapi.controllers;

import com.algaworks.algafood.api.exceptionhandler.ApiError;
import com.algaworks.algafood.api.model.in.GrupoInputDTO;
import com.algaworks.algafood.api.model.out.GrupoOutputDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

    @ApiOperation("Lista os grupos")
    ResponseEntity<List<GrupoOutputDTO>> listar();

    @ApiOperation("Busca um grupo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID do grupo inválido",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    })
    })
    ResponseEntity<GrupoOutputDTO> buscar(
            @ApiParam(value = "ID de um grupo", example = "1")
            final Long id
    );

    @ApiOperation("Cadastra um grupo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Grupo cadastrado")
    })
    ResponseEntity<GrupoOutputDTO> adicionar(
            @ApiParam(name = "corpo", value = "Representação de uma nova cidade")
            final GrupoInputDTO grupoInputDTO
    );

    @ApiOperation("Atualiza um grupo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grupo atualizado"),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
            })
    })
    ResponseEntity<GrupoOutputDTO> atualizar(
            @ApiParam(value = "ID de um grupo", example = "1")
            final Long id,
            @ApiParam(name = "corpo", value = "Representação de um grupo com os novos dados")
            final GrupoInputDTO grupoInputDTO
    );

    @ApiOperation("Exclui um grupo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Grupo excluído"),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    })
    })
    ResponseEntity<Void> excluir(@ApiParam(value = "ID de um grupo", example = "1") final Long id);

}
