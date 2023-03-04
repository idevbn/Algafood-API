package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
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

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@TestPropertySource("/application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CadastroRestauranteIT {

    private static final String VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE
            = "Violação de regra de negócio";

    private static final String DADOS_INVALIDOS_PROBLEM_TITLE = "Dados inválidos";

    private static final String PREFIXO_CAMINHO_INCORRETO = "/json/incorreto/";

    private static final String RESTAURANTES = "/restaurantes";

    private static final int RESTAURANTE_ID_INEXISTENTE = 100;

    private final CadastroRestauranteService restauranteService;

    private final RestauranteRepository restauranteRepository;

    private final CozinhaRepository cozinhaRepository;

    private final DatabaseCleaner databaseCleaner;

    private Restaurante kingOfSpices;

    private String jsonRestauranteCorreto;

    private String jsonRestauranteSemFrete;

    private String jsonRestauranteSemCozinha;

    private String jsonRestauranteComCozinhaInexistente;

    @LocalServerPort
    private int port;

    @Autowired
    public CadastroRestauranteIT(
            final CadastroRestauranteService restauranteService,
            final RestauranteRepository restauranteRepository,
            final CozinhaRepository cozinhaRepository,
            final DatabaseCleaner databaseCleaner) {
        this.restauranteService = restauranteService;
        this.restauranteRepository = restauranteRepository;
        this.cozinhaRepository = cozinhaRepository;
        this.databaseCleaner = databaseCleaner;
    }

    @BeforeEach
    public void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = this.port;
        RestAssured.basePath = RESTAURANTES;

        jsonRestauranteCorreto = ResourceUtils
                .getContentFromResource(
                        "/json/correto/restaurante-punjab-cozinha-indiana.json"
                );

        jsonRestauranteComCozinhaInexistente = ResourceUtils
                .getContentFromResource(
                        PREFIXO_CAMINHO_INCORRETO +
                                "restaurante-punjab-cozinha-indiana-com-cozinha-inexistente.json"
                );

        jsonRestauranteSemCozinha = ResourceUtils
                .getContentFromResource(
                        PREFIXO_CAMINHO_INCORRETO +
                                "restaurante-punjab-cozinha-indiana-sem-cozinha.json"
                );

        jsonRestauranteSemFrete = ResourceUtils
                .getContentFromResource(
                        PREFIXO_CAMINHO_INCORRETO +
                                "restaurante-punjab-cozinha-indiana-sem-frete.json"
                );

        this.databaseCleaner.clearTables();
        this.prepararDados();
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarRestaurantes() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarRestaurante() {
        given()
                .body(jsonRestauranteCorreto)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRetornarStatus400_QuandoCadastrarRestauranteSemTaxaFrete() {
        given()
                .body(jsonRestauranteSemFrete)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
    }

    @Test
    public void deveRetornarStatus400_QuandoCadastrarRestauranteComCozinhaInexistente() {
        given()
                .body(jsonRestauranteComCozinhaInexistente)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE));
    }

    @Test
    public void deveRetornarRespostaEStatusCorretos_QuandoConsultarRestauranteExistente() {
        given()
                .pathParam("restauranteId", kingOfSpices.getId())
                .accept(ContentType.JSON)
                .when()
                .get("/{restauranteId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", equalTo(kingOfSpices.getNome()));
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarRestauranteInexistente() {
        given()
                .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
                .accept(ContentType.JSON)
                .when()
                .get("/{restauranteId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepararDados() {
        final Cozinha cozinhaBrasileira = new Cozinha();
        cozinhaBrasileira.setNome("Brasileira");
        this.cozinhaRepository.save(cozinhaBrasileira);

        final Cozinha cozinhaIndiana = new Cozinha();
        cozinhaIndiana.setNome("Indiana");
        this.cozinhaRepository.save(cozinhaIndiana);

        kingOfSpices = new Restaurante();
        kingOfSpices.setNome("King of Spices");
        kingOfSpices.setTaxaFrete(new BigDecimal(10));
        kingOfSpices.setCozinha(cozinhaIndiana);
        restauranteRepository.save(kingOfSpices);

        final Restaurante comidaMineiraRestaurante = new Restaurante();
        comidaMineiraRestaurante.setNome("Comida Mineira");
        comidaMineiraRestaurante.setTaxaFrete(new BigDecimal(10));
        comidaMineiraRestaurante.setCozinha(cozinhaBrasileira);
        restauranteRepository.save(comidaMineiraRestaurante);
    }

}
