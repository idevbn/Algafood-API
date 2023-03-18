package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.api.assembler.UsuarioOutputDTOAssembler;
import com.algaworks.algafood.api.model.out.UsuarioOutputDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/restaurantes/{id}/responsaveis")
public class RestauranteUsuarioResponsavelController {

    private final CadastroRestauranteService restauranteService;
    private final UsuarioOutputDTOAssembler assembler;

    @Autowired
    public RestauranteUsuarioResponsavelController(final CadastroRestauranteService restauranteService,
                                                   final UsuarioOutputDTOAssembler assembler) {
        this.restauranteService = restauranteService;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioOutputDTO>> listar(@PathVariable("id") final Long id) {
        final Restaurante restaurante = this.restauranteService.buscarOuFalhar(id);

        final Set<Usuario> responsaveis = restaurante.getResponsaveis();

        final List<UsuarioOutputDTO> usuariosOutputDTOS = this.assembler
                .toCollectionModel(responsaveis);

        final ResponseEntity<List<UsuarioOutputDTO>> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(usuariosOutputDTOS);

        return response;
    }

    @PutMapping(value = "/{usuarioId}")
    public ResponseEntity<Void> associar(@PathVariable("id") final Long id,
                                         @PathVariable("usuarioId") final Long usuarioId) {
        this.restauranteService.associarResponsavel(id, usuarioId);

        final ResponseEntity<Void> response = ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

        return response;
    }

    @DeleteMapping(value = "/{usuarioId}")
    public ResponseEntity<Void> desassociar(@PathVariable("id") final Long id,
                                            @PathVariable("usuarioId") final Long usuarioId) {
        this.restauranteService.desassociarResponsavel(id, usuarioId);

        final ResponseEntity<Void> response = ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

        return response;
    }

}
