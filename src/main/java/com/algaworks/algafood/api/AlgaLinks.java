package com.algaworks.algafood.api;

import com.algaworks.algafood.api.controllers.*;
import org.springframework.hateoas.*;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AlgaLinks {

    public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM)
    );

    public static final TemplateVariables PROJECAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("projecao", TemplateVariable.VariableType.REQUEST_PARAM)
    );

    public Link linkToPedidos(final String rel) {
        final TemplateVariables filtroVariables = new TemplateVariables(
                new TemplateVariable("codigo", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("nomerestaurante", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("nomeCliente", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("valorTotal", TemplateVariable.VariableType.REQUEST_PARAM)
        );

        final String pedidosUrl = linkTo(PedidoController.class).toUri().toString();

        return Link.of(UriTemplate.of(pedidosUrl,
                PAGINACAO_VARIABLES.concat(filtroVariables)), rel);
    }

    public Link linkToConfirmacaoPedido(final String codigoPedido, final String rel) {
        return linkTo(methodOn(FluxoPedidoController.class).confirmar(codigoPedido))
                .withRel(rel);
    }

    public Link linkToEntregaPedido(final String codigoPedido, final String rel) {
        return linkTo(methodOn(FluxoPedidoController.class).entregar(codigoPedido))
                .withRel(rel);
    }

    public Link linkToCancelamentoPedido(final String codigoPedido, final String rel) {
        return linkTo(methodOn(FluxoPedidoController.class).cancelar(codigoPedido))
                .withRel(rel);
    }

    public Link linkToRestaurante(final Long restauranteId, final String rel) {
        return linkTo(methodOn(RestauranteController.class)
                .buscar(restauranteId)).withRel(rel);
    }

    public Link linkToRestaurante(final Long restauranteId) {
        return linkToRestaurante(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestaurantes(final String rel) {
        final String restaurantesUrl = linkTo(RestauranteController.class).toUri().toString();

        return Link.of(UriTemplate.of(restaurantesUrl, PROJECAO_VARIABLES), rel);
    }

    public Link linkToRestaurantes() {
        return linkToRestaurantes(IanaLinkRelations.SELF.value());
    }

    public Link linkToRestauranteFormasPagamento(final Long restauranteId, final String rel) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class)
                .listar(restauranteId)).withRel(rel);
    }

    public Link linkToRestauranteFormasPagamento(final Long restauranteId) {
        return linkToRestauranteFormasPagamento(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToFormasPagamento(final String rel) {
        return linkTo(FormaPagamentoController.class).withRel(rel);
    }

    public Link linkToFormasPagamento() {
        return linkToFormasPagamento(IanaLinkRelations.SELF.value());
    }

    public Link linkToCozinha(final Long cozinhaId, final String rel) {
        return linkTo(methodOn(CozinhaController.class)
                .buscar(cozinhaId)).withRel(rel);
    }

    public Link linkToCozinha(final Long cozinhaId) {
        return linkToCozinha(cozinhaId, IanaLinkRelations.SELF.value());
    }

    public Link linkToUsuario(final Long usuarioId, final String rel) {
        return linkTo(methodOn(UsuarioController.class)
                .buscar(usuarioId)).withRel(rel);
    }

    public Link linkToUsuario(final Long usuarioId) {
        return linkToUsuario(usuarioId, IanaLinkRelations.SELF.value());
    }

    public Link linkToUsuarios(final String rel) {
        return linkTo(UsuarioController.class).withRel(rel);
    }

    public Link linkToUsuarios() {
        return linkToUsuarios(IanaLinkRelations.SELF.value());
    }

    public Link linkToGruposUsuario(final Long usuarioId, final String rel) {
        return linkTo(methodOn(UsuarioGrupoController.class)
                .listar(usuarioId)).withRel(rel);
    }

    public Link linkToGruposUsuario(final Long usuarioId) {
        return linkToGruposUsuario(usuarioId, IanaLinkRelations.SELF.value());
    }

    public Link linkToResponsaveisRestaurante(final Long restauranteId, final String rel) {
        return linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
                .listar(restauranteId)).withRel(rel);
    }

    public Link linkToResponsaveisRestaurante(final Long restauranteId) {
        return linkToResponsaveisRestaurante(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToFormaPagamento(final Long formaPagamentoId, final String rel) {
        return linkTo(methodOn(FormaPagamentoController.class)
                .buscar(formaPagamentoId, null)).withRel(rel);
    }

    public Link linkToFormaPagamento(final Long formaPagamentoId) {
        return linkToFormaPagamento(formaPagamentoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestauranteFormaPagamentoDesassociacao(final Long restauranteId,
                                                             final Long formaPagamentoId,
                                                             final String rel) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class)
                .desassociar(restauranteId, formaPagamentoId))
                .withRel(rel);
    }

    public Link linkToRestauranteFormaPagamentoAssociacao(final Long restauranteId,
                                                          final String rel) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class)
                .associar(restauranteId, null))
                .withRel(rel);
    }

    public Link linkToRestauranteUsuarioResponsavelDesassociacao(final Long restauranteId,
                                                                 final Long formaPagamentoId,
                                                                 final String rel) {
        return linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
                .desassociar(restauranteId, formaPagamentoId))
                .withRel(rel);
    }

    public Link linkToRestauranteUsuarioResponsavelAssociacao(final Long restauranteId,
                                                              final String rel) {
        return linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
                .associar(restauranteId, null))
                .withRel(rel);
    }

    public Link linkToCidade(final Long cidadeId, final String rel) {
        return linkTo(methodOn(CidadeController.class)
                .buscar(cidadeId)).withRel(rel);
    }

    public Link linkToCidade(final Long cidadeId) {
        return linkToCidade(cidadeId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCidades(final String rel) {
        return linkTo(CidadeController.class).withRel(rel);
    }

    public Link linkToCidades() {
        return linkToCidades(IanaLinkRelations.SELF.value());
    }

    public Link linkToEstado(final Long estadoId, final String rel) {
        return linkTo(methodOn(EstadoController.class)
                .buscar(estadoId)).withRel(rel);
    }

    public Link linkToEstado(final Long estadoId) {
        return linkToEstado(estadoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToEstados(final String rel) {
        return linkTo(EstadoController.class).withRel(rel);
    }

    public Link linkToEstados() {
        return linkToEstados(IanaLinkRelations.SELF.value());
    }

    public Link linkToProduto(final Long restauranteId, final Long produtoId, final String rel) {
        return linkTo(methodOn(RestauranteProdutoController.class)
                .buscar(restauranteId, produtoId))
                .withRel(rel);
    }

    public Link linkToProdutos(final Long restauranteId, final String rel) {
        return linkTo(methodOn(RestauranteProdutoController.class)
                .listar(restauranteId, null)).withRel(rel);
    }

    public Link linkToProdutos(final Long restauranteId) {
        return linkToProdutos(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToProduto(final Long restauranteId, final Long produtoId) {
        return linkToProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToFotoProduto(final Long restauranteId,
                                  final Long produtoId,
                                  final String rel) {
        return linkTo(methodOn(RestauranteFotoProdutoController.class)
                .recuperarFoto(restauranteId, produtoId)).withRel(rel);
    }

    public Link linkToFotoProduto(final Long restauranteId, final Long produtoId) {
        return linkToFotoProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCozinhas(final String rel) {
        return linkTo(CozinhaController.class).withRel(rel);
    }

    public Link linkToCozinhas() {
        return linkToCozinhas(IanaLinkRelations.SELF.value());
    }

    public Link linkToRestauranteAbertura(final Long restauranteId, final String rel) {
        return linkTo(methodOn(RestauranteController.class)
                .abrir(restauranteId)).withRel(rel);
    }

    public Link linkToRestauranteFechamento(final Long restauranteId, final String rel) {
        return linkTo(methodOn(RestauranteController.class)
                .fechar(restauranteId)).withRel(rel);
    }

    public Link linkToRestauranteInativacao(final Long restauranteId, final String rel) {
        return linkTo(methodOn(RestauranteController.class)
                .inativar(restauranteId)).withRel(rel);
    }

    public Link linkToRestauranteAtivacao(final Long restauranteId, final String rel) {
        return linkTo(methodOn(RestauranteController.class)
                .ativar(restauranteId)).withRel(rel);
    }

}
