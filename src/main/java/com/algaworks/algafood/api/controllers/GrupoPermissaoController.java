package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.api.assembler.PermissaoOutputDTOAssembler;
import com.algaworks.algafood.api.model.out.PermissaoOutputDTO;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/grupos/{id}/permissoes")
public class GrupoPermissaoController {

    private final CadastroGrupoService service;
    private final PermissaoOutputDTOAssembler assembler;

    @Autowired
    public GrupoPermissaoController(final CadastroGrupoService service,
                                    final PermissaoOutputDTOAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<List<PermissaoOutputDTO>> listar(
            @PathVariable("id") final Long id
    ) {
        final Grupo grupo = this.service.buscarOuFalhar(id);

        final Collection<Permissao> permissoes = grupo
                .getPermissoes();

        final List<PermissaoOutputDTO> permissoesOutputDTOS = this.assembler
                .toCollectionModel(permissoes);

        final ResponseEntity<List<PermissaoOutputDTO>> response = ResponseEntity
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
