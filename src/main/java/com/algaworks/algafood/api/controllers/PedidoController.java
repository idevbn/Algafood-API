package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.api.assembler.PedidoOutputDTOAssembler;
import com.algaworks.algafood.api.model.out.PedidoOutputDTO;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    private final PedidoRepository repository;
    private final EmissaoPedidoService service;
    private final PedidoOutputDTOAssembler assembler;

    @Autowired
    public PedidoController(final PedidoRepository repository,
                            final EmissaoPedidoService service,
                            final PedidoOutputDTOAssembler assembler) {
        this.repository = repository;
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<List<PedidoOutputDTO>> listar() {
        final List<Pedido> pedidos = this.repository.findAll();

        final List<PedidoOutputDTO> pedidosOutputDTOS = this.assembler
                .toCollectionModel(pedidos);

        final ResponseEntity<List<PedidoOutputDTO>> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(pedidosOutputDTOS);

        return response;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PedidoOutputDTO> buscar(@PathVariable("id") final Long id) {
        final Pedido pedido = this.service.buscarOuFalhar(id);

        final PedidoOutputDTO pedidoOutputDTO = this.assembler.toModel(pedido);

        final ResponseEntity<PedidoOutputDTO> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(pedidoOutputDTO);

        return response;
    }

}
