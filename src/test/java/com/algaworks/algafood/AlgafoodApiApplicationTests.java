package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CadastroCozinhaIntegrationTest {

	private final CadastroCozinhaService cozinhaService;

	@Autowired
	 CadastroCozinhaIntegrationTest(final CadastroCozinhaService cozinhaService) {
		this.cozinhaService = cozinhaService;
	}

	@Test
	public void testarCadastroCozinhaComSucesso() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");

		novaCozinha = cozinhaService.salvar(novaCozinha);

		assertThat(novaCozinha).isNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}

	@Test
	public void testarCadastroCozinhaSemNome() {
		final Cozinha novaCozinha = new Cozinha();

		novaCozinha.setNome(null);

		final ConstraintViolationException erroEsperado =
				Assertions.assertThrows(ConstraintViolationException.class, () -> {
					this.cozinhaService.salvar(novaCozinha);
				});

		assertThat(erroEsperado).isNotNull();
	}

}
