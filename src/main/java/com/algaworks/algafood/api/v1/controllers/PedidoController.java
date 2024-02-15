package com.algaworks.algafood.api.v1.controllers;

import com.algaworks.algafood.api.v1.assembler.PedidoInputDTODisassembler;
import com.algaworks.algafood.api.v1.assembler.PedidoOutputDTOAssembler;
import com.algaworks.algafood.api.v1.assembler.PedidoResumoOutputDTOAssembler;
import com.algaworks.algafood.api.v1.model.in.PedidoInputDTO;
import com.algaworks.algafood.api.v1.model.out.PedidoOutputDTO;
import com.algaworks.algafood.api.v1.model.out.PedidoResumoOutputDTO;
import com.algaworks.algafood.api.v1.openapi.controllers.PedidoControllerOpenApi;
import com.algaworks.algafood.core.data.PageWrapper;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.algaworks.algafood.infraestructure.repository.spec.PedidoSpecs;
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

import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(path = "/v1/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {

    private final PedidoRepository repository;
    private final EmissaoPedidoService service;
    private final PedidoOutputDTOAssembler assembler;
    private final PedidoResumoOutputDTOAssembler assemblerPedidoResumo;
    private final PedidoInputDTODisassembler disassembler;
    private final PagedResourcesAssembler<Pedido> pagedResourcesAssembler;
    private final AlgaSecurity algaSecurity;


    @Autowired
    public PedidoController(final PedidoRepository repository,
                            final EmissaoPedidoService service,
                            final PedidoOutputDTOAssembler assembler,
                            final PedidoResumoOutputDTOAssembler assemblerPedidoResumo,
                            final PedidoInputDTODisassembler disassembler,
                            final PagedResourcesAssembler<Pedido> pagedResourcesAssembler,
                            final AlgaSecurity algaSecurity) {
        this.repository = repository;
        this.service = service;
        this.assembler = assembler;
        this.assemblerPedidoResumo = assemblerPedidoResumo;
        this.disassembler = disassembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.algaSecurity = algaSecurity;
    }

    @GetMapping
    @CheckSecurity.Pedidos.PodePesquisar
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
    @CheckSecurity.Pedidos.PodeBuscar
    public ResponseEntity<PedidoOutputDTO> buscar(@PathVariable("codigo") final String codigo) {
        final Pedido pedido = this.service.buscarOuFalhar(codigo);

        final PedidoOutputDTO pedidoOutputDTO = this.assembler.toModel(pedido);

        final ResponseEntity<PedidoOutputDTO> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(pedidoOutputDTO);

        return response;
    }

    @PostMapping
    @CheckSecurity.Pedidos.PodeCriar
    public ResponseEntity<PedidoOutputDTO> adicionar(
            @Valid @RequestBody final PedidoInputDTO pedidoInputDTO
    ) {
        try {
            final Pedido pedido = this.disassembler.toDomainModel(pedidoInputDTO);

            pedido.setCliente(new Usuario());
            pedido.getCliente().setId(this.algaSecurity.getUsuarioId());

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
        final Map<String, String> mapeamento = Map.of(
                "codigo", "codigo",
                "nomerestaurante", "restaurante.nome",
                "nomeCliente", "cliente.nome",
                "valorTotal", "valorTotal"
        );

        final Pageable translatedPage = PageableTranslator.translate(pageable, mapeamento);

        return translatedPage;
    }

}
