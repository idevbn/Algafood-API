package com.algaworks.algafood.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {

    @Bean
    public Docket apiDocket() {
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
                .apiInfo(this.apiInfo())
                .tags(new Tag("Cidades", "Gerencia as cidades"));

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

    private List<Response> globalGetResponseMessages() {

        final Response responseStatusInternalServerError = new ResponseBuilder()
                .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
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
                .description("Requisição inválida (erro do cliente)")
                .build();

        final Response responseStatusInternalServerError = new ResponseBuilder()
                .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .description("Erro interno no servidor")
                .build();

        final Response responseStatusNotAcceptable = new ResponseBuilder()
                .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
                .build();

        final Response responseStatusUnsupportedMediaType = new ResponseBuilder()
                .code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
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
                .description("Requisição inválida (erro do cliente)")
                .build();

        final Response responseStatusInternalServerError = new ResponseBuilder()
                .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .description("Erro interno no servidor")
                .build();

        final List<Response> responses = Arrays.asList(
                responseStatusBadRequest,
                responseStatusInternalServerError
        );

        return responses;
    }

}
