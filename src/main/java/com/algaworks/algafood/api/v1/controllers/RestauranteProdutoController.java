package com.algaworks.algafood.api.v1.controllers;

import com.algaworks.algafood.api.v1.assembler.ProdutoInputDTODisassembler;
import com.algaworks.algafood.api.v1.assembler.ProdutoOutputDTOAssembler;
import com.algaworks.algafood.api.v1.model.in.ProdutoInputDTO;
import com.algaworks.algafood.api.v1.model.out.ProdutoOutputDTO;
import com.algaworks.algafood.api.v1.openapi.controllers.RestauranteProdutoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/restaurantes/{id}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {

    private final ProdutoRepository repository;
    private final CadastroProdutoService produtoService;
    private final CadastroRestauranteService restauranteService;
    private final ProdutoOutputDTOAssembler assembler;
    private final ProdutoInputDTODisassembler disassembler;

    @Autowired
    public RestauranteProdutoController(final ProdutoRepository repository,
                                        final CadastroProdutoService produtoService,
                                        final CadastroRestauranteService restauranteService,
                                        final ProdutoOutputDTOAssembler assembler,
                                        final ProdutoInputDTODisassembler disassembler) {
        this.repository = repository;
        this.produtoService = produtoService;
        this.restauranteService = restauranteService;
        this.assembler = assembler;
        this.disassembler = disassembler;
    }

    @GetMapping
    @CheckSecurity.Restaurantes.PodeConsultar
    public ResponseEntity<CollectionModel<ProdutoOutputDTO>> listar(
            @PathVariable("id") final Long id,
            @RequestParam(required = false) final Boolean incluirInativos
    ) {
        final Restaurante restaurante = this.restauranteService.buscarOuFalhar(id);

        List<Produto> produtos;

        if (incluirInativos) {
            produtos = this.repository.findByRestaurante(restaurante);
        } else {
            produtos = this.repository.findAtivosByRestaurante(restaurante);
        }

        final CollectionModel<ProdutoOutputDTO> produtosOutputDTOS = this
                .assembler.toCollectionModel(produtos);

        final ResponseEntity<CollectionModel<ProdutoOutputDTO>> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(produtosOutputDTOS);

        return response;
    }

    @GetMapping("/{produtoId}")
    @CheckSecurity.Restaurantes.PodeConsultar
    public ResponseEntity<ProdutoOutputDTO> buscar(@PathVariable final Long id,
                                                   @PathVariable final Long produtoId) {
        final Produto produto = this.produtoService.buscarOuFalhar(id, produtoId);

        final ProdutoOutputDTO produtoOutputDTO = this.assembler.toModel(produto);

        final ResponseEntity<ProdutoOutputDTO> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(produtoOutputDTO);

        return response;
    }

    @PostMapping
    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    public ResponseEntity<ProdutoOutputDTO> adicionar(
            @PathVariable final Long id,
            @RequestBody @Valid final ProdutoInputDTO produtoInputDTO
    ) {
        final Restaurante restaurante = this.restauranteService.buscarOuFalhar(id);

        Produto produto = this.disassembler.toDomainObject(produtoInputDTO);
        produto.setRestaurante(restaurante);

        produto = this.produtoService.salvar(produto);

        final ProdutoOutputDTO produtoOutputDTO = this.assembler.toModel(produto);

        final ResponseEntity<ProdutoOutputDTO> response = ResponseEntity
                .status(HttpStatus.CREATED)
                .body(produtoOutputDTO);

        return response;
    }

    @PutMapping("/{produtoId}")
    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    public ResponseEntity<ProdutoOutputDTO> atualizar(
            @PathVariable final Long id,
            @PathVariable final Long produtoId,
            @RequestBody @Valid ProdutoInputDTO produtoInputDTO
    ) {
        Produto produtoAtual = this.produtoService.buscarOuFalhar(id, produtoId);

        this.disassembler.copyToDomainObject(produtoInputDTO, produtoAtual);

        produtoAtual = this.produtoService.salvar(produtoAtual);

        final ProdutoOutputDTO produtoOutputDTO = this.assembler.toModel(produtoAtual);

        final ResponseEntity<ProdutoOutputDTO> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(produtoOutputDTO);

        return response;
    }

}
