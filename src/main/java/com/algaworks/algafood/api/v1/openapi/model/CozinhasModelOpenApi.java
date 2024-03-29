package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.model.out.CozinhaOutputDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe responsável pela documentação do DTO de saída
 * {@link CozinhaOutputDTO}, utilizando paginação.
 */
@Getter
@Setter
public class CozinhasModelOpenApi extends PageModelOpenApi<CozinhaOutputDTO> {
}
