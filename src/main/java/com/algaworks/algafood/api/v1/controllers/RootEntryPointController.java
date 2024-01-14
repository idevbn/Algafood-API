package com.algaworks.algafood.api.v1.controllers;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;
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
    private final AlgaSecurity algaSecurity;

    @Autowired
    public RootEntryPointController(final AlgaLinks algaLinks,
                                    final AlgaSecurity algaSecurity) {
        this.algaLinks = algaLinks;
        this.algaSecurity = algaSecurity;
    }

    @GetMapping
    public RootEntryPointModel root() {
        final RootEntryPointModel rootEntryPointModel = new RootEntryPointModel();

        if (this.algaSecurity.podeConsultarCozinhas()) {
            rootEntryPointModel
                    .add(this.algaLinks.linkToCozinhas("cozinhas"));
        }

        if (this.algaSecurity.podePesquisarPedidos()) {
            rootEntryPointModel
                    .add(this.algaLinks.linkToPedidos("pedidos"));
        }

        if (this.algaSecurity.podeConsultarRestaurantes()) {
            rootEntryPointModel
                    .add(this.algaLinks.linkToRestaurantes("restaurantes"));
        }

        if (this.algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            rootEntryPointModel
                    .add(this.algaLinks.linkToGrupos("grupos"))
                    .add(this.algaLinks.linkToUsuarios("usuarios"))
                    .add(this.algaLinks.linkToPermissoes("permissoes"));
        }

        if (this.algaSecurity.podeConsultarFormasPagamento()) {
            rootEntryPointModel
                    .add(this.algaLinks.linkToFormasPagamento("formas-pagamento"));
        }

        if (this.algaSecurity.podeConsultarEstados()) {
            rootEntryPointModel
                    .add(this.algaLinks.linkToEstados("estados"));
        }

        if (this.algaSecurity.podeConsultarCidades()) {
            rootEntryPointModel
                    .add(this.algaLinks.linkToCidades("cidades"));
        }

        if (this.algaSecurity.podeConsultarEstatisticas()) {
            rootEntryPointModel
                    .add(this.algaLinks.linkToEstatisticas("estatisticas"));
        }

        return rootEntryPointModel;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
    }

}
