package com.algaworks.algafood.api.v1.controllers;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.GrupoOutputDTOAssembler;
import com.algaworks.algafood.api.v1.model.out.GrupoOutputDTO;
import com.algaworks.algafood.api.v1.openapi.controllers.UsuarioGrupoControllerOpenApi;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "/v1/usuarios/{id}/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {

    private final GrupoOutputDTOAssembler assembler;
    private final CadastroUsuarioService service;
    private final AlgaLinks algaLinks;
    private final AlgaSecurity algaSecurity;

    @Autowired
    public UsuarioGrupoController(final GrupoOutputDTOAssembler assembler,
                                  final CadastroUsuarioService service,
                                  final AlgaLinks algaLinks,
                                  final AlgaSecurity algaSecurity) {
        this.assembler = assembler;
        this.service = service;
        this.algaLinks = algaLinks;
        this.algaSecurity = algaSecurity;
    }

    @GetMapping
    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    public ResponseEntity<CollectionModel<GrupoOutputDTO>> listar(
            @PathVariable("id") final Long usuarioId
    ) {
        final Usuario usuario = this.service.buscarOuFalhar(usuarioId);

        final Set<Grupo> grupos = usuario.getGrupos();

        final CollectionModel<GrupoOutputDTO> gruposOutputDTOS = this.assembler
                .toCollectionModel(grupos)
                .removeLinks();

        if (this.algaSecurity.podeEditarUsuariosGruposPermissoes()) {
            gruposOutputDTOS
                    .add(this.algaLinks.linkToUsuarioGrupoAssociacao(usuarioId, "associar"));

            gruposOutputDTOS.getContent().forEach(grupoOutputDTO -> {
                grupoOutputDTO.add(this.algaLinks.linkToUsuarioGrupoDesassociacao(
                        usuarioId, grupoOutputDTO.getId(), "desassociar"));
            });
        }

        final ResponseEntity<CollectionModel<GrupoOutputDTO>> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(gruposOutputDTOS);

        return response;
    }

    @PutMapping(value = "/{grupoId}")
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
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
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
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
