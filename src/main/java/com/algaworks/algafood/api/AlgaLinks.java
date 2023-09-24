package com.algaworks.algafood.api;

import com.algaworks.algafood.api.controllers.PedidoController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class AlgaLinks {

    public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM)
    );

    public Link linkToPedidos() {
        final TemplateVariables filtroVariables = new TemplateVariables(
                new TemplateVariable("codigo", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("nomerestaurante", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("nomeCliente", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("valorTotal", TemplateVariable.VariableType.REQUEST_PARAM)
        );

        final String pedidosUrl = linkTo(PedidoController.class).toUri().toString();

        return Link.of(UriTemplate.of(pedidosUrl,
                PAGINACAO_VARIABLES.concat(filtroVariables)), "pedidos");
    }

}
