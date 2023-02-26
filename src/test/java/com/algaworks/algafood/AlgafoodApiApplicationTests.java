package com.algaworks.algafood;

import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

	@BeforeEach
	public void setup() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = this.port;
		RestAssured.basePath = COZINHAS;
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarUmaCozinha() {

		given()
				.accept(ContentType.JSON)
				.when()
				.get()
				.then()
				.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveConter2Cozinhas_QuandoRetornarCozinhas() {

		given()
				.accept(ContentType.JSON)
				.when()
				.get()
				.then()
				.body("", hasSize(2))
				.body("nome", hasItems("Indiana", "Tailandesa"));
	}

	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinhas() {

		given()
				.body("{\"nome\": \"Chinesa\"}")
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.when()
				.post()
				.then()
				.statusCode(HttpStatus.CREATED.value());
	}

}
