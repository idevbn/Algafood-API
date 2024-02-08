package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.model.in.ProdutoInputDTO;
import com.algaworks.algafood.api.v1.model.out.ProdutoOutputDTO;
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

@Tag(name = "Produtos")
@SecurityRequirement(name = "security_auth")
public interface RestauranteProdutoControllerOpenApi {

    @Operation(summary = "Lista os produtos de um restaurante", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID do restaurante inválido", content = {
                    @Content(schema = @Schema(ref = "ApiError")) }),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "ApiError")) }),
    })
    ResponseEntity<CollectionModel<ProdutoOutputDTO>> listar(
            @Parameter(description = "ID do restaurante", example = "1", required = true)
            final Long restauranteId,
            @Parameter(description = "Incluir inativos", example = "false", required = false)
            final Boolean incluirInativos
    );

    @Operation(summary = "Busca um produto de um restaurante", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID do restaurante ou produto inválido", content = {
                    @Content(schema = @Schema(ref = "ApiError")) }),
            @ApiResponse(responseCode = "404", description = "Produto de restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "ApiError")) }),
    })
    ResponseEntity<ProdutoOutputDTO> buscar(
            @Parameter(description = "ID do restaurante", example = "1", required = true)
            final Long restauranteId,
            @Parameter(description = "ID do produto", example = "1", required = true)
            final Long produtoId
    );

    @Operation(summary = "Cadastra um produto de um restaurante", responses = {
            @ApiResponse(responseCode = "201", description = "Produto cadastrado"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "ApiError")) }),
    })
    ResponseEntity<ProdutoOutputDTO> adicionar(
            @Parameter(description = "ID do restaurante", example = "1", required = true)
            final Long restauranteId,
            @RequestBody(description = "Representação de um novo produto", required = true)
            final ProdutoInputDTO produtoInputDTO
    );

    @Operation(summary = "Atualiza um produto de um restaurante", responses = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado"),
            @ApiResponse(responseCode = "404", description = "Produto de restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "ApiError")) }),
    })
    ResponseEntity<ProdutoOutputDTO> atualizar(
            @Parameter(description = "ID do restaurante", example = "1", required = true)
            final Long restauranteId,
            @Parameter(description = "ID do produto", example = "1", required = true)
            final Long produtoId,
            @RequestBody(
                    description = "Representação de um produto com os novos dados",
                    required = true
            )
            final ProdutoInputDTO produtoInputDTO
    );

}
