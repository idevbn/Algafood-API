package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import com.algaworks.algafood.util.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@TestPropertySource("/application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CadastroCozinhaIT {

	private static final String COZINHAS = "/cozinhas";

	private final CadastroCozinhaService cozinhaService;

	private final CozinhaRepository cozinhaRepository;

	private final DatabaseCleaner databaseCleaner;

	@LocalServerPort
	private int port;

	@Autowired
	private CadastroCozinhaIT(final CadastroCozinhaService cozinhaService,
							  final CozinhaRepository cozinhaRepository,
							  final DatabaseCleaner databaseCleaner) {
		this.cozinhaService = cozinhaService;
		this.cozinhaRepository = cozinhaRepository;
		this.databaseCleaner = databaseCleaner;
	}

	@BeforeEach
	public void setup() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = this.port;
		RestAssured.basePath = COZINHAS;

		this.databaseCleaner.clearTables();
		this.prepararDados();
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

	@Test
	public void deveRetornarRespostaeStatusCorretos_QuandoConsultarCozinhasExistentes() {

		given()
				.pathParam("cozinhaId", 1)
				.accept(ContentType.JSON)
				.when()
				.get("/{cozinhaId}")
				.then()
				.statusCode(HttpStatus.OK.value())
				.body("nome", equalTo("Tailandesa"));
	}

	@Test
	public void deveRetornarRespostaeStatus404_QuandoConsultarCozinhasInexistentes() {

		given()
				.pathParam("cozinhaId", 100)
				.accept(ContentType.JSON)
				.when()
				.get("/{cozinhaId}")
				.then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}

	private void prepararDados() {
		final Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Tailandesa");
		this.cozinhaRepository.save(cozinha1);

		final Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Indiana");
		this.cozinhaRepository.save(cozinha2);
	}

}
