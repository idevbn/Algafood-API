package com.algaworks.algafood;

import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.flywaydb.core.Flyway;
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

	private final Flyway flyway;

	@LocalServerPort
	private int port;

	@Autowired
	private CadastroCozinhaIT(final CadastroCozinhaService cozinhaService,
							  final Flyway flyway) {
		this.cozinhaService = cozinhaService;
		this.flyway = flyway;
	}

	/**
	 * A ideia de injetar o flyway no método setup (executado antes de cada
	 * método de teste) está relacionada com a questão da independência entre
	 * os testes. A migração executada no método setup, anotado com @BeforeEach,
	 * faz com que os dados não sejam acumulados e, assim, o método que testa o cadastro
	 * não influi no método que verifica o tamanho (listagem das cozinhas).
	 *
	 * Dessa forma, garante-se uma indenpendência entre os dados.
	 *
	 * Por que isso foi adotado?
	 * R: A ordem de execução de testes do JUnit não é garantida, e caso esteja funcionando
	 * no modelo atual, caso houvesse uma alteração em um nome de teste, p.ex. o método
	 * deveRetornarStatus201_QuandoCadastrarCozinhas fosse modificado para
	 * testeRetornarStatus201_QuandoCadastrarCozinhas, caso a ordem fosse alterada na execução
	 * e esse método fosse testado antes do método de listagem, o tamanho do array de cozinhas
	 * mudaria (seria acrescido de 1). Assim os testes estariam DEPENDENTES entre si e falhariam.
	 */
	@BeforeEach
	public void setup() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = this.port;
		RestAssured.basePath = COZINHAS;

		this.flyway.migrate();
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
