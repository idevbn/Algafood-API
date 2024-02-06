package com.algaworks.algafood.api.v1.openapi.model;

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
public class PageableModelOpenApi {

    private int page;

    private int size;

    private List<String> sort;

}
