package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.model.out.PedidoResumoOutputDTO;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("PedidosResumoModel")
public class PedidosResumoModelOpenApi extends PageModelOpenApi<PedidoResumoOutputDTO>{
}
