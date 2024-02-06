package com.algaworks.algafood.api.v1.openapi.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Classe genérica com caráter de documentação para recursos que utilizam
 * {@link Page}.
 */
@Getter
@Setter
public class PageModelOpenApi<T> {

    private List<T> content;

    private Long size;

    private Long totalElements;

    private Long totalPages;

    private Long number;

}
