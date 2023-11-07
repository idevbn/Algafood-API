package com.algaworks.algafood.api.v1.controllers;

import com.algaworks.algafood.api.v1.AlgaLinks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

    private final AlgaLinks algaLinks;

    @Autowired
    public RootEntryPointController(final AlgaLinks algaLinks) {
        this.algaLinks = algaLinks;
    }

    @GetMapping
    public RootEntryPointModel root() {
        final RootEntryPointModel rootEntryPointModel = new RootEntryPointModel();

        rootEntryPointModel
                .add(this.algaLinks.linkToCozinhas("cozinhas"))
                .add(this.algaLinks.linkToPedidos("pedidos"))
                .add(this.algaLinks.linkToRestaurantes("restaurantes"))
                .add(this.algaLinks.linkToGrupos("grupos"))
                .add(this.algaLinks.linkToUsuarios("usuarios"))
                .add(this.algaLinks.linkToPermissoes("permissoes"))
                .add(this.algaLinks.linkToFormasPagamento("formas-pagamento"))
                .add(this.algaLinks.linkToEstados("estados"))
                .add(this.algaLinks.linkToCidades("cidades"))
                .add(this.algaLinks.linkToEstatisticas("estatisticas"));

        return rootEntryPointModel;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
    }

}
