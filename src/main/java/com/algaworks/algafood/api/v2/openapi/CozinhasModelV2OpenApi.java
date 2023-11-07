package com.algaworks.algafood.api.v2.openapi;

import com.algaworks.algafood.api.v2.model.CozinhaOutputDTOV2;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@Getter
@Setter
@ApiModel("CozinhasModel")
public class CozinhasModelV2OpenApi {

    private CozinhasEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelV2OpenApi page;

    @Data
    @ApiModel("CozinhasEmbeddedModel")
    public class CozinhasEmbeddedModelOpenApi {

        private List<CozinhaOutputDTOV2> cozinhas;

    }

}
