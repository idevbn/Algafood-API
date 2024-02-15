package com.algaworks.algafood.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação utilizada em nível de classe cuja implementação {@link ValorZeroIncluiDescricaoValidator}
 * valida se, para o caso do atributo taxaFrete ter o valor 0, a descrição (nome) p
 * assada equivale ao valor atribuído no campo descricaoObrigatoria.
 * <br />
 *
 * EXEMPLO:
 *
 * <br />
 * {{@code @ValorZeroIncluiDescricao(valorField} = "taxaFrete",
 *         descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
 * public class Restaurante {}
 * <br />
 *
 * Caso uma requisição seja feita, e o valor passado no atributo "nome" do corpo não corresponda
 * ao atributo descricaoObrigatoria = "Frete Grátis", será disparado um erro de validação.
 *
 */
@Target({ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValorZeroIncluiDescricaoValidator.class})
public @interface ValorZeroIncluiDescricao {

    String message() default "{ValorZeroIncluiDescricao}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String valorField();

    String descricaoField();

    String descricaoObrigatoria();

}
