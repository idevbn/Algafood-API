package com.algaworks.algafood.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação que define a estrutura d da validação de um múltiplo.
 * É utilizado um valor padrão para o número = 5, mas poderia-se
 * omitir um valor default.
 * <p>
 * Assim como a {@link TaxaFrete}, essa é uma anotação apenas como
 * ferramenta de aprendizagem e não deverá ser utilizada.
 * <p>
 * Exemplos de uso:
 * <p>
 * 1. @Multiplo
 *    private BigDecimal taxaFrete;
 *    <p>
 *    Da forma como está definido acima, será utilizado o valor default da anotação,
 *    que é igual a 5.
 *    <p>
 *    Faz-se uma requisição passando o valor "taxaFrete": 12.
 *    Assim, espera-se o erro: "Taxa de frete do restaurante deve ser um valor múltiplo de 5",
 *    já que os valores <b>não são múltiplos</b>. Ver {@link MultiploValidator}.
 *    <p>
 *
 *  2. @Multiplo(numero = 7)
 *     private BigDecimal taxaFrete;
 *     <p>
 *     Faz-se uma requisição passando o valor "taxaFrete": 21.
 *     Uma vez que os valores <b>são múltiplos</b>, a operação é aceita.
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {MultiploValidator.class})
public @interface Multiplo {

    String message() default "{Multiplo}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int numero() default 5;

}
