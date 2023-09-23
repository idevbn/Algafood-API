package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controllers.PedidoController;
import com.algaworks.algafood.api.controllers.RestauranteController;
import com.algaworks.algafood.api.controllers.UsuarioController;
import com.algaworks.algafood.api.model.out.PedidoResumoOutputDTO;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoResumoOutputDTOAssembler
        extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoOutputDTO> {

    private final ModelMapper modelMapper;

    public PedidoResumoOutputDTOAssembler(final ModelMapper modelMapper) {
        super(PedidoController.class, PedidoResumoOutputDTO.class);
        this.modelMapper = modelMapper;
    }

    @Override
    public PedidoResumoOutputDTO toModel(final Pedido pedido) {
        final PedidoResumoOutputDTO pedidoResumoOutputDTO = this
                .createModelWithId(pedido.getId(), pedido);
        this.modelMapper.map(pedido, pedidoResumoOutputDTO);

        final TemplateVariables pageVariables = new TemplateVariables(
                new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM)
        );

        final TemplateVariables filtroVariables = new TemplateVariables(
                new TemplateVariable("codigo", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("nomerestaurante", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("nomeCliente", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("valorTotal", TemplateVariable.VariableType.REQUEST_PARAM)
        );

        final String pedidosUrl = linkTo(PedidoController.class).toUri().toString();

        pedidoResumoOutputDTO.add(Link.of(UriTemplate.of(pedidosUrl,
                pageVariables.concat(filtroVariables)), "pedidos"));

        pedidoResumoOutputDTO.getRestaurante().add(linkTo(methodOn(RestauranteController.class)
                .buscar(pedido.getRestaurante().getId())).withSelfRel());

        pedidoResumoOutputDTO.getCliente().add(linkTo(methodOn(UsuarioController.class)
                .buscar(pedido.getCliente().getId())).withSelfRel());

        return pedidoResumoOutputDTO;
    }

}
