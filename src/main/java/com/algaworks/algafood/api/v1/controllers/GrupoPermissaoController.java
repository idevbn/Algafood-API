package com.algaworks.algafood.api.v1.controllers;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.PermissaoOutputDTOAssembler;
import com.algaworks.algafood.api.v1.model.out.PermissaoOutputDTO;
import com.algaworks.algafood.api.v1.openapi.controllers.GrupoPermissaoControllerOpenApi;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/grupos/{id}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    private final CadastroGrupoService service;
    private final PermissaoOutputDTOAssembler assembler;
    private final AlgaLinks algaLinks;


    @Autowired
    public GrupoPermissaoController(final CadastroGrupoService service,
                                    final PermissaoOutputDTOAssembler assembler,
                                    final AlgaLinks algaLinks) {
        this.service = service;
        this.assembler = assembler;
        this.algaLinks = algaLinks;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<PermissaoOutputDTO>> listar(
            @PathVariable("id") final Long grupoId
    ) {
        final Grupo grupo = this.service.buscarOuFalhar(grupoId);

        final Collection<Permissao> permissoes = grupo
                .getPermissoes();

        final CollectionModel<PermissaoOutputDTO> permissoesOutputDTOS = this.assembler
                .toCollectionModel(permissoes)
                .removeLinks()
                .add(algaLinks.linkToGrupoPermissoes(grupoId))
                .add(algaLinks.linkToGrupoPermissaoAssociacao(grupoId, "associar"));

        permissoesOutputDTOS.getContent().forEach(permissaoOutputDTO -> {
            permissaoOutputDTO.add(this.algaLinks.linkToGrupoPermissaoDesassociacao(
                    grupoId, permissaoOutputDTO.getId(), "desassociar"));
        });

        final ResponseEntity<CollectionModel<PermissaoOutputDTO>> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(permissoesOutputDTOS);

        return response;
    }

    @PutMapping(value = "/{permissaoId}")
    public ResponseEntity<Void> associar(
            @PathVariable("id") final Long id,
            @PathVariable("permissaoId") final Long permissaoId
    ) {
        this.service.associarPermissao(id, permissaoId);

        final ResponseEntity<Void> response = ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

        return response;
    }

    @DeleteMapping(value = "/{permissaoId}")
    public ResponseEntity<Void> desassociar(
            @PathVariable("id") final Long id,
            @PathVariable("permissaoId") final Long permissaoId
    ) {
        this.service.desassociarPermissao(id, permissaoId);

        final ResponseEntity<Void> response = ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

        return response;
    }

}
