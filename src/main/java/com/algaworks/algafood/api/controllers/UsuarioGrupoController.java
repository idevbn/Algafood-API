package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.api.assembler.GrupoOutputDTOAssembler;
import com.algaworks.algafood.api.model.out.GrupoOutputDTO;
import com.algaworks.algafood.api.openapi.controllers.UsuarioGrupoControllerOpenApi;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/usuarios/{id}/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {

    private final GrupoOutputDTOAssembler assembler;
    private final CadastroUsuarioService service;

    @Autowired
    public UsuarioGrupoController(final GrupoOutputDTOAssembler assembler,
                                  final CadastroUsuarioService service) {
        this.assembler = assembler;
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<GrupoOutputDTO>> listar(
            @PathVariable("id") final Long id
    ) {
        final Usuario usuario = this.service.buscarOuFalhar(id);

        final Set<Grupo> grupos = usuario.getGrupos();

        final List<GrupoOutputDTO> gruposOutputDTOS = this.assembler
                .toCollectionModel(grupos);

        final ResponseEntity<List<GrupoOutputDTO>> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(gruposOutputDTOS);

        return response;
    }

    @PutMapping(value = "/{grupoId}")
    public ResponseEntity<Void> associar(
            @PathVariable("id") final Long id,
            @PathVariable("grupoId") final Long grupoId
    ) {
        this.service.associarGrupo(id, grupoId);

        final ResponseEntity<Void> response = ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

        return response;
    }

    @DeleteMapping(value = "/{grupoId}")
    public ResponseEntity<Void> desassociar(
            @PathVariable("id") final Long id,
            @PathVariable("grupoId") final Long grupoId
    ) {
        this.service.desassociarGrupo(id, grupoId);

        final ResponseEntity<Void> response = ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

        return response;
    }

}
