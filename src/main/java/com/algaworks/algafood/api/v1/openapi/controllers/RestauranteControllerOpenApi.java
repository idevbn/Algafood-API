package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.in.RestauranteInputDTO;
import com.algaworks.algafood.api.v1.model.out.RestauranteApenasNomeOutputDTO;
import com.algaworks.algafood.api.v1.model.out.RestauranteBasicoOutputDTO;
import com.algaworks.algafood.api.v1.model.out.RestauranteOutputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Tag(name = "Restaurantes")
@SecurityRequirement(name = "security_auth")
public interface RestauranteControllerOpenApi {

    @Operation(summary = "Lista os restaurantes",
            parameters = {
            @Parameter(name = "projecao",
                    description = "Nome da projeção",
                    example = "apenas-nome",
                    in = ParameterIn.QUERY,
                    required = false
            )
    })
    ResponseEntity<CollectionModel<RestauranteBasicoOutputDTO>> listar();

//    @JsonView(RestauranteView.Resumo.class)
//    public ResponseEntity<CollectionModel<RestauranteBasicoOutputDTO>> listarResumido();

    @Operation(hidden = true)
    ResponseEntity<CollectionModel<RestauranteApenasNomeOutputDTO>> listarApenasNomes();

    @Operation(summary = "Busca um restaurante por ID", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID do restaurante inválido", content = {
                    @Content(schema = @Schema(ref = "ApiError")) }),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "ApiError")) }),
    })
    ResponseEntity<RestauranteOutputDTO> buscar(
            @Parameter(description = "ID de um restaurante", example = "1", required = true)
            final Long restauranteId
    );

    @Operation(summary = "Cadastra um restaurante", responses = {
            @ApiResponse(responseCode = "201", description = "Restaurante cadastrado"),
    })
    ResponseEntity<RestauranteOutputDTO> adicionar(
            @RequestBody(description = "Representação de um novo restaurante", required = true)
            final RestauranteInputDTO restauranteInputDTO
    );

    @Operation(summary = "Atualiza um restaurante por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "ApiError")) }),
    })
    ResponseEntity<RestauranteOutputDTO> atualizar(
            @Parameter(description = "ID de um restaurante", example = "1", required = true)
            final Long restauranteId,
            @RequestBody(
                    description = "Representação de um restaurante com os novos dados",
                    required = true
            )
            final RestauranteInputDTO restauranteInputDTO
    );

    @Operation(summary = "Atualiza parcialmente um restaurante por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "ApiError")) }),
    })
    ResponseEntity<RestauranteOutputDTO> atualizarParcialmente(
            @Parameter(description = "ID de um restaurante", example = "1", required = true)
            final Long id,
            @RequestBody(
                    description = "Representação do(s) campo(s) do restaurante com os novos dados",
                    required = true
            )
            final Map<String, Object> campos,
            @Parameter(hidden = true)
            final HttpServletRequest request
    );

    @Operation(summary = "Ativa um restaurante por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurante ativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "ApiError")) }),
    })
    ResponseEntity<Void> ativar(
            @Parameter(description = "ID de um restaurante", example = "1", required = true)
            final Long restauranteId
    );

    @Operation(summary = "Desativa um restaurante por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurante inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "ApiError")) }),
    })
    ResponseEntity<Void> inativar(
            @Parameter(description = "ID de um restaurante", example = "1", required = true)
            final Long restauranteId
    );

    @Operation(summary = "Ativa múltiplos restaurantes", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso"),
    })
    ResponseEntity<Void> ativarMultiplos(
            @RequestBody(description = "IDs de restaurantes", required = true)
            final List<Long> restauranteIds
    );

    @Operation(summary = "Inativa múltiplos restaurantes", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso"),
    })
    ResponseEntity<Void> inativarMultiplos(
            @RequestBody(description = "IDs de restaurantes", required = true)
            final List<Long> restauranteIds
    );

    @Operation(summary = "Abre um restaurante por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurante aberto com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "ApiError")) }),
    })
    ResponseEntity<Void> abrir(
            @Parameter(description = "ID de um restaurante", example = "1", required = true)
            final Long restauranteId
    );

    @Operation(summary = "Fecha um restaurante por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurante fechado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "ApiError")) }),
    })
    ResponseEntity<Void> fechar(
            @Parameter(description = "ID de um restaurante", example = "1", required = true)
            final Long restauranteId
    );

}
