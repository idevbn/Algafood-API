package com.algaworks.algafood.api.v2.openapi;

import com.algaworks.algafood.api.v2.model.CidadeOutputDTOV2;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
public class CidadesModelV2OpenApi {

    private CidadesEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Data
    public class CidadesEmbeddedModelOpenApi {

        private List<CidadeOutputDTOV2> cidades;

    }

}
