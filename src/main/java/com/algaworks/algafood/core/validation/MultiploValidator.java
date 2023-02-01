package com.algaworks.algafood.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

/**
 * Classe de implementação de um {@link Multiplo}
 * que verifica com base no valor escolhido (que por default é 5)
 * se o valor passado na requisição é múltiplo do valor escolhido.
 */
public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {

    private int numeroMultiplo;

    @Override
    public void initialize(final Multiplo constraintAnnotation) {
        this.numeroMultiplo = constraintAnnotation.numero();
    }

    @Override
    public boolean isValid(
            final Number value,
            final ConstraintValidatorContext constraintValidatorContext
    ) {
        boolean valido = true;

        if (value != null) {
            final BigDecimal valorDecimal = BigDecimal.valueOf(value.doubleValue());

            final BigDecimal multiploDecimal = BigDecimal.valueOf(this.numeroMultiplo);

            final BigDecimal resto = valorDecimal.remainder(multiploDecimal);

            valido = BigDecimal.ZERO.compareTo(resto) == 0;
        }

        return valido;
    }

}
