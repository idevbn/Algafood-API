package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.api.assembler.PedidoInputDTODisassembler;
import com.algaworks.algafood.api.assembler.PedidoOutputDTOAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoOutputDTOAssembler;
import com.algaworks.algafood.api.model.in.PedidoInputDTO;
import com.algaworks.algafood.api.model.out.PedidoOutputDTO;
import com.algaworks.algafood.api.model.out.PedidoResumoOutputDTO;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    private final PedidoRepository repository;
    private final EmissaoPedidoService service;
    private final PedidoOutputDTOAssembler assembler;
    private final PedidoResumoOutputDTOAssembler assemblerPedidoResumo;
    private final PedidoInputDTODisassembler disassembler;

    @Autowired
    public PedidoController(final PedidoRepository repository,
                            final EmissaoPedidoService service,
                            final PedidoOutputDTOAssembler assembler,
                            final PedidoResumoOutputDTOAssembler assemblerPedidoResumo,
                            final PedidoInputDTODisassembler disassembler) {
        this.repository = repository;
        this.service = service;
        this.assembler = assembler;
        this.assemblerPedidoResumo = assemblerPedidoResumo;
        this.disassembler = disassembler;
    }

    @GetMapping
    public ResponseEntity<List<PedidoResumoOutputDTO>> listar() {
        final List<Pedido> pedidos = this.repository.findAll();

        final List<PedidoResumoOutputDTO> pedidosResumoOutputDTOS = this
                .assemblerPedidoResumo.toCollectionModel(pedidos);

        final ResponseEntity<List<PedidoResumoOutputDTO>> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(pedidosResumoOutputDTOS);

        return response;
    }

    @GetMapping(value = "/{codigo}")
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

}
