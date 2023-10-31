package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.api.assembler.PermissaoOutputDTOAssembler;
import com.algaworks.algafood.api.model.out.PermissaoOutputDTO;
import com.algaworks.algafood.api.openapi.controllers.PermissaoControllerOpenApi;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController implements PermissaoControllerOpenApi {

    private final PermissaoRepository repository;
    private final PermissaoOutputDTOAssembler assembler;

    @Autowired
    public PermissaoController(final PermissaoRepository repository,
                               final PermissaoOutputDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<PermissaoOutputDTO>> listar() {
        final List<Permissao> todasPermissoes = this.repository.findAll();

        final CollectionModel<PermissaoOutputDTO> permissoesOutputDTO = this.assembler
                .toCollectionModel(todasPermissoes);

        final ResponseEntity<CollectionModel<PermissaoOutputDTO>> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(permissoesOutputDTO);

        return response;
    }

}
