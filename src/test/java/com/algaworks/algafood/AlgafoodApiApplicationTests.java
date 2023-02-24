package com.algaworks.algafood;

import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CadastroCozinhaIT {

	private static final String COZINHAS = "/cozinhas";

	private final CadastroCozinhaService cozinhaService;

	@LocalServerPort
	private int port;

	@Autowired
	protected CadastroCozinhaIT(final CadastroCozinhaService cozinhaService) {
		this.cozinhaService = cozinhaService;
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarUmaCozinha() {
		enableLoggingOfRequestAndResponseIfValidationFails();

		given()
				.basePath(COZINHAS)
				.port(this.port)
				.accept(ContentType.JSON)
				.when()
				.get()
				.then()
				.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveConter2Cozinhas_QuandoRetornarCozinhas() {
		enableLoggingOfRequestAndResponseIfValidationFails();

		given()
				.basePath(COZINHAS)
				.port(this.port)
				.accept(ContentType.JSON)
				.when()
				.get()
				.then()
				.body("", hasSize(2))
				.body("nome", hasItems("Indiana", "Tailandesa"));
	}

}
