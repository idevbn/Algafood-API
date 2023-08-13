package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.out.CozinhaOutputDTO;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe responsável pela documentação do DTO de saída
 * {@link CozinhaOutputDTO}, utilizando paginação.
 */
@Getter
@Setter
@ApiModel("CozinhasModel")
public class CozinhasModelOpenApi extends PageModelOpenApi<CozinhaOutputDTO> {
}
