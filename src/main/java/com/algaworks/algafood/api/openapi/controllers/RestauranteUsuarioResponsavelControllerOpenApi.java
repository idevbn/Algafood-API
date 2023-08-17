package com.algaworks.algafood.api.openapi.controllers;

import com.algaworks.algafood.api.exceptionhandler.ApiError;
import com.algaworks.algafood.api.model.out.UsuarioOutputDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteUsuarioResponsavelControllerOpenApi {

    @ApiOperation("Lista os usuários responsáveis associados a restaurante")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "404",
                    description = "Restaurante não encontrado",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    }
            )
    })
    ResponseEntity<List<UsuarioOutputDTO>> listar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            final Long restauranteId
    );

    @ApiOperation("Desassociação de restaurante com usuário responsável")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Desassociação realizada com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Restaurante ou usuário não encontrado",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    }
            )
    })
    ResponseEntity<Void> desassociar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            final Long restauranteId,

            @ApiParam(value = "ID do usuário", example = "1", required = true)
            final Long usuarioId
    );

    @ApiOperation("Associação de restaurante com usuário responsável")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Associação realizada com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Restaurante ou usuário não encontrado",
                    content = {
                            @Content(schema = @Schema(implementation = ApiError.class))
                    }
            )
    })
    ResponseEntity<Void> associar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            final Long restauranteId,

            @ApiParam(value = "ID do usuário", example = "1", required = true)
            final Long usuarioId
    );

}
