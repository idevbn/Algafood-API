package com.algaworks.algafood;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
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
	protected CadastroCozinhaIntegrationTest(final CadastroCozinhaService cozinhaService) {
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

	@Test
	public void deveFalhar_QuandoExcluirCozinhaEmUso() {
		final EntidadeEmUsoException erroEsperado =
				Assertions.assertThrows(EntidadeEmUsoException.class, () -> {
					this.cozinhaService.excluir(1L);
				});

		assertThat(erroEsperado).isNotNull();
	}

	@Test
	public void deveFalhar_QuandoExcluirCozinhaInexistente() {


		final CozinhaNaoEncontradaException erroEsperado =
				Assertions.assertThrows(CozinhaNaoEncontradaException.class, () -> {
					this.cozinhaService.excluir(100L);
				});

		assertThat(erroEsperado).isNotNull();
	}

}
