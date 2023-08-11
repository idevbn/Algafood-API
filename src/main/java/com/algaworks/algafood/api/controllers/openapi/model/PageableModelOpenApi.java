package com.algaworks.algafood.api.controllers.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Classe criada para fins de documentação.
 * Os atributos correspondem aos atributos de query param
 * passados na requisição.
 *
 * @author Idevaldo Neto <idevbn@gmail.com>
 */
@Getter
@Setter
@ApiModel("Pageable")
public class PageableModelOpenApi {

    @ApiModelProperty(example = "0", value = "Número da página (começa em 0)")
    private int page;

    @ApiModelProperty(example = "10", value = "Número de elementos por página")
    private int size;

    @ApiModelProperty(example = "nome,asc", value = "Nome da propriedade para ordenação")
    private List<String> sort;

}
