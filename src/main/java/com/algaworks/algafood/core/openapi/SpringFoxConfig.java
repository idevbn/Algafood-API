package com.algaworks.algafood.core.openapi;

import com.algaworks.algafood.api.exceptionhandler.ApiError;
import com.algaworks.algafood.api.model.out.CozinhaOutputDTO;
import com.algaworks.algafood.api.openapi.model.CozinhasModelOpenApi;
import com.algaworks.algafood.api.openapi.model.PageableModelOpenApi;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {

    @Bean
    public Docket apiDocket() {
        final TypeResolver typeResolver = new TypeResolver();

        final Docket docket = new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, globalGetResponseMessages())
                .globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
                .additionalModels(typeResolver.resolve(ApiError.class))
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(Page.class, CozinhaOutputDTO.class),
                        CozinhasModelOpenApi.class
                ))
                .apiInfo(this.apiInfo())
                .tags(new Tag("Cidades", "Gerencia as cidades"))
                .tags(new Tag("Grupos", "Gerencia os grupos"));

        return docket;
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("AlgaFood API")
                .description("API aberta para clientes e restaurantes")
                .version("1")
                .contact(new Contact("AlgaWorks", "https://www.algaworks.com", "contato@algaworks.com"))
                .build();
    }

    @Bean
    public JacksonModuleRegistrar springFoxJacksonConfig() {
        return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
    }
    private Consumer<RepresentationBuilder> getProblemaModelReference() {
        return r -> r.model(m -> m.name("ApiError")
                .referenceModel(ref -> ref.key(k -> k.qualifiedModelName(
                        q -> q.name("ApiError").namespace("com.algaworks.algafood.api.exceptionhandler")))));
    }


    private List<Response> globalGetResponseMessages() {

        final Response responseStatusInternalServerError = new ResponseBuilder()
                .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .representation(MediaType.APPLICATION_JSON)
                .apply(getProblemaModelReference())
                .description("Erro interno do Servidor")
                .build();

        final Response responseStatusNotAcceptable = new ResponseBuilder()
                .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                .description("Recurso não possui representação que pode ser aceita pelo consumidor")
                .build();

        final List<Response> responses = Arrays
                .asList(responseStatusInternalServerError, responseStatusNotAcceptable);

        return responses;
    }

    private List<Response> globalPostPutResponseMessages() {

        final Response responseStatusBadRequest = new ResponseBuilder()
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .representation(MediaType.APPLICATION_JSON)
                .apply(getProblemaModelReference())
                .description("Requisição inválida (erro do cliente)")
                .build();

        final Response responseStatusInternalServerError = new ResponseBuilder()
                .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .representation(MediaType.APPLICATION_JSON)
                .apply(getProblemaModelReference())
                .description("Erro interno no servidor")
                .build();

        final Response responseStatusNotAcceptable = new ResponseBuilder()
                .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
                .build();

        final Response responseStatusUnsupportedMediaType = new ResponseBuilder()
                .code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
                .representation(MediaType.APPLICATION_JSON)
                .apply(getProblemaModelReference())
                .description("Requisição recusada porque o corpo está em um formato não suportado")
                .build();

        final List<Response> responses = Arrays.asList(
                responseStatusBadRequest,
                responseStatusInternalServerError,
                responseStatusNotAcceptable,
                responseStatusUnsupportedMediaType
        );

        return responses;
    }

    private List<Response> globalDeleteResponseMessages() {

        final Response responseStatusBadRequest = new ResponseBuilder()
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .representation(MediaType.APPLICATION_JSON)
                .apply(getProblemaModelReference())
                .description("Requisição inválida (erro do cliente)")
                .build();

        final Response responseStatusInternalServerError = new ResponseBuilder()
                .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .representation(MediaType.APPLICATION_JSON)
                .apply(getProblemaModelReference())
                .description("Erro interno no servidor")
                .build();

        final List<Response> responses = Arrays.asList(
                responseStatusBadRequest,
                responseStatusInternalServerError
        );

        return responses;
    }

}
