package com.algaworks.algafood.api.v1.openapi.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinksModelOpenApi {

    private final LinkModel rel;

    public LinksModelOpenApi(final LinkModel rel) {
        this.rel = rel;
    }

    @Getter
    @Setter
    private class LinkModel {
        private String href;
        private boolean templated;
    }

}
