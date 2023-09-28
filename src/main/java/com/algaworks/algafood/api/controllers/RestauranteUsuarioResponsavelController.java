package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.assembler.UsuarioOutputDTOAssembler;
import com.algaworks.algafood.api.model.out.UsuarioOutputDTO;
import com.algaworks.algafood.api.openapi.controllers.RestauranteUsuarioResponsavelControllerOpenApi;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "/restaurantes/{id}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioResponsavelController
        implements RestauranteUsuarioResponsavelControllerOpenApi {

    private final CadastroRestauranteService restauranteService;
    private final UsuarioOutputDTOAssembler assembler;
    private final AlgaLinks algaLinks;

    @Autowired
    public RestauranteUsuarioResponsavelController(final CadastroRestauranteService restauranteService,
                                                   final UsuarioOutputDTOAssembler assembler,
                                                   final AlgaLinks algaLinks) {
        this.restauranteService = restauranteService;
        this.assembler = assembler;
        this.algaLinks = algaLinks;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<UsuarioOutputDTO>> listar(@PathVariable("id") final Long id) {
        final Restaurante restaurante = this.restauranteService.buscarOuFalhar(id);

        final Set<Usuario> responsaveis = restaurante.getResponsaveis();

        final CollectionModel<UsuarioOutputDTO> usuariosOutputDTOS = this.assembler
                .toCollectionModel(responsaveis)
                .removeLinks()
                .add(this.algaLinks.linkToResponsaveisRestaurante(id));

        final ResponseEntity<CollectionModel<UsuarioOutputDTO>> response = ResponseEntity
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
