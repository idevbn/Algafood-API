package com.algaworks.algafood.core.web;

import org.springframework.http.MediaType;

/**
 * Classe com os tipos customizados utilizados na aplicação
 *
 * @author Idevaldo Neto <idevbn@gmail.com>
 */
public class AlgaMediaTypes {

    public static final String V1_APPLICATION_JSON_VALUE = "application/vnd.algafood.v1+json";

    public static final MediaType V1_APPLICATION_JSON = MediaType.valueOf(V1_APPLICATION_JSON_VALUE);

}
