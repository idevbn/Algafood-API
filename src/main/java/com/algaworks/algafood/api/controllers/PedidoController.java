package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.api.assembler.PedidoInputDTODisassembler;
import com.algaworks.algafood.api.assembler.PedidoOutputDTOAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoOutputDTOAssembler;
import com.algaworks.algafood.api.model.in.PedidoInputDTO;
import com.algaworks.algafood.api.model.out.PedidoOutputDTO;
import com.algaworks.algafood.api.model.out.PedidoResumoOutputDTO;
import com.algaworks.algafood.api.openapi.controllers.PedidoControllerOpenApi;
import com.algaworks.algafood.core.data.PageWrapper;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.algaworks.algafood.infraestructure.repository.spec.PedidoSpecs;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {

    private final PedidoRepository repository;
    private final EmissaoPedidoService service;
    private final PedidoOutputDTOAssembler assembler;
    private final PedidoResumoOutputDTOAssembler assemblerPedidoResumo;
    private final PedidoInputDTODisassembler disassembler;
    private final PagedResourcesAssembler<Pedido> pagedResourcesAssembler;


    @Autowired
    public PedidoController(final PedidoRepository repository,
                            final EmissaoPedidoService service,
                            final PedidoOutputDTOAssembler assembler,
                            final PedidoResumoOutputDTOAssembler assemblerPedidoResumo,
                            final PedidoInputDTODisassembler disassembler,
                            final PagedResourcesAssembler<Pedido> pagedResourcesAssembler) {
        this.repository = repository;
        this.service = service;
        this.assembler = assembler;
        this.assemblerPedidoResumo = assemblerPedidoResumo;
        this.disassembler = disassembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(
                    value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                    name = "campos",
                    paramType = "query",
                    type = "string"
            )
    })
    public ResponseEntity<PagedModel<PedidoResumoOutputDTO>> pesquisar(
            @PageableDefault() Pageable pageable,
            final PedidoFilter filtro
    ) {
        final Pageable pageableTraduzido = this.traduzirPageable(pageable);

        Page<Pedido> pedidos = this.repository
                .findAll(PedidoSpecs.usandoFiltro(filtro), pageableTraduzido);

        pedidos = new PageWrapper<>(pedidos, pageable);

        final PagedModel<PedidoResumoOutputDTO> pedidosPage = this.pagedResourcesAssembler
                .toModel(pedidos, this.assemblerPedidoResumo);

        final ResponseEntity<PagedModel<PedidoResumoOutputDTO>> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(pedidosPage);

        return response;
    }

    @GetMapping(value = "/{codigo}")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                    name = "campos",
                    paramType = "query",
                    type = "string"
            )
    })
    public ResponseEntity<PedidoOutputDTO> buscar(@PathVariable("codigo") final String codigo) {
        final Pedido pedido = this.service.buscarOuFalhar(codigo);

        final PedidoOutputDTO pedidoOutputDTO = this.assembler.toModel(pedido);

        final ResponseEntity<PedidoOutputDTO> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(pedidoOutputDTO);

        return response;
    }

    @PostMapping
    public ResponseEntity<PedidoOutputDTO> adicionar(
            @Valid @RequestBody final PedidoInputDTO pedidoInputDTO
    ) {
        try {
            final Pedido pedido = this.disassembler.toDomainModel(pedidoInputDTO);

            pedido.setCliente(new Usuario());
            pedido.getCliente().setId(1L);

            this.service.emitir(pedido);

            final PedidoOutputDTO pedidoOutputDTO = this.assembler.toModel(pedido);

            final ResponseEntity<PedidoOutputDTO> response = ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(pedidoOutputDTO);

            return response;
        } catch (final EntidadeNaoEncontradaException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    private Pageable traduzirPageable(final Pageable pageable) {
        final ImmutableMap<String, String> mapeamento = ImmutableMap.of(
                "codigo", "codigo",
                "nomerestaurante", "restaurante.nome",
                "nomeCliente", "cliente.nome",
                "valorTotal", "valorTotal"
        );

        final Pageable translatedPage = PageableTranslator.translate(pageable, mapeamento);

        return translatedPage;
    }

}
