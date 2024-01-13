package com.algaworks.algafood.api.v1.controllers;

import com.algaworks.algafood.api.v1.assembler.UsuarioInputDTODisassembler;
import com.algaworks.algafood.api.v1.assembler.UsuarioOutputDTOAssembler;
import com.algaworks.algafood.api.v1.model.in.SenhaInputDTO;
import com.algaworks.algafood.api.v1.model.in.UsuarioComSenhaInputDTO;
import com.algaworks.algafood.api.v1.model.in.UsuarioInputDTO;
import com.algaworks.algafood.api.v1.model.out.UsuarioOutputDTO;
import com.algaworks.algafood.api.v1.openapi.controllers.UsuarioControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController implements UsuarioControllerOpenApi {

    private final UsuarioRepository repository;
    private final CadastroUsuarioService service;
    private final UsuarioInputDTODisassembler inputDTODisassembler;
    private final UsuarioOutputDTOAssembler outputDTOAssembler;

    @Autowired
    public UsuarioController(final UsuarioRepository repository,
                             final CadastroUsuarioService service,
                             final UsuarioInputDTODisassembler inputDTODisassembler,
                             final UsuarioOutputDTOAssembler outputDTOAssembler) {
        this.repository = repository;
        this.service = service;
        this.inputDTODisassembler = inputDTODisassembler;
        this.outputDTOAssembler = outputDTOAssembler;
    }

    @GetMapping
    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    public ResponseEntity<CollectionModel<UsuarioOutputDTO>> listar() {
        final List<Usuario> usuarios = this.repository.findAll();

        final CollectionModel<UsuarioOutputDTO> usuariosOutputDTOS = this.outputDTOAssembler
                .toCollectionModel(usuarios);

        final ResponseEntity<CollectionModel<UsuarioOutputDTO>> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(usuariosOutputDTOS);

        return response;
    }

    @GetMapping(value = "/{id}")
    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    public ResponseEntity<UsuarioOutputDTO> buscar(@PathVariable("id") final Long id) {
        final Usuario usuario = this.service.buscarOuFalhar(id);

        final UsuarioOutputDTO usuarioOutputDTO = this.outputDTOAssembler
                .toModel(usuario);

        final ResponseEntity<UsuarioOutputDTO> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioOutputDTO);

        return response;
    }

    @PostMapping
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    public ResponseEntity<UsuarioOutputDTO> adicionar(
            @RequestBody @Valid final UsuarioComSenhaInputDTO usuarioComSenhaInputDTO
    ) {
        final Usuario usuario = this.inputDTODisassembler
                .toDomainObject(usuarioComSenhaInputDTO);

        final Usuario usuarioSalvo = this.service.salvar(usuario);

        final UsuarioOutputDTO usuarioOutputDTO = this.outputDTOAssembler
                .toModel(usuarioSalvo);

        final ResponseEntity<UsuarioOutputDTO> response = ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioOutputDTO);

        return response;
    }

    @PutMapping(value = "/{id}")
    @CheckSecurity.UsuariosGruposPermissoes.PodeAlterarUsuario
    public ResponseEntity<UsuarioOutputDTO> atualizar(
            @PathVariable("id") final Long id,
            @RequestBody @Valid final UsuarioInputDTO usuarioInputDTO
    ) {
        final Usuario usuarioAtual = this.service.buscarOuFalhar(id);

        this.inputDTODisassembler.copyToDomainObject(usuarioInputDTO, usuarioAtual);

        final Usuario usuarioSalvo = this.service.salvar(usuarioAtual);

        final UsuarioOutputDTO usuarioOutputDTO = this.outputDTOAssembler
                .toModel(usuarioSalvo);

        final ResponseEntity<UsuarioOutputDTO> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioOutputDTO);

        return response;
    }

    @PutMapping(value = "/{id}/senha")
    @CheckSecurity.UsuariosGruposPermissoes.PodeAlterarPropriaSenha
    public ResponseEntity<Void> alterarSenha(
            @PathVariable("id") final Long id,
            @RequestBody @Valid final SenhaInputDTO senha
            ) {
        this.service.alterarSenha(id, senha.getSenhaAtual(), senha.getNovaSenha());

        final ResponseEntity<Void> response = ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

        return response;
    }

}
