package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;
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

	private static final int ID_COZINHA_INEXISTENTE = 100;

	private final CadastroCozinhaService cozinhaService;

	private final CozinhaRepository cozinhaRepository;

	private final DatabaseCleaner databaseCleaner;

	private int quantidadeDeCozinhasCadastradas;

	private String jsonCorretoCozinhaChinesa;

	private Cozinha cozinhaIndiana;

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

		jsonCorretoCozinhaChinesa = ResourceUtils
				.getContentFromResource("/json/correto/cozinha-chinesa.json");

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
	public void deveConterQuantidadeCorretaDeCozinhas_QuandoRetornarCozinhas() {

		given()
				.accept(ContentType.JSON)
				.when()
				.get()
				.then()
				.body("", hasSize(this.quantidadeDeCozinhasCadastradas))
				.body("nome", hasItems("Indiana", "Tailandesa"));
	}

	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinhas() {

		given()
				.body(jsonCorretoCozinhaChinesa)
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
				.pathParam("cozinhaId", 2)
				.accept(ContentType.JSON)
				.when()
				.get("/{cozinhaId}")
				.then()
				.statusCode(HttpStatus.OK.value())
				.body("nome", equalTo(cozinhaIndiana.getNome()));
	}

	@Test
	public void deveRetornarRespostaeStatus404_QuandoConsultarCozinhasInexistentes() {

		given()
				.pathParam("cozinhaId", ID_COZINHA_INEXISTENTE)
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

		cozinhaIndiana = new Cozinha();
		cozinhaIndiana.setNome("Indiana");
		this.cozinhaRepository.save(cozinhaIndiana);

		quantidadeDeCozinhasCadastradas = (int) this.cozinhaRepository.count();
	}

}
