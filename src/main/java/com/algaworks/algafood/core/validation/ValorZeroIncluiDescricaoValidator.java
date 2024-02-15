package com.algaworks.algafood.core.validation;

import org.springframework.beans.BeanUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ValidationException;
import java.math.BigDecimal;

public class ValorZeroIncluiDescricaoValidator implements
        ConstraintValidator<ValorZeroIncluiDescricao, Object> {

    private String valorField;
    private String descricaoField;
    private String descricaoObrigatoria;

    @Override
    public void initialize(final ValorZeroIncluiDescricao constraint) {
        this.valorField = constraint.valorField();
        this.descricaoField = constraint.descricaoField();
        this.descricaoObrigatoria = constraint.descricaoObrigatoria();
    }

    @Override
    public boolean isValid(final Object objValidacao, final ConstraintValidatorContext context) {
        boolean valido = true;

        try {
             final BigDecimal valor = (BigDecimal) BeanUtils
                     .getPropertyDescriptor(objValidacao.getClass(), valorField)
                    .getReadMethod()
                    .invoke(objValidacao);

            final String descricao = (String) BeanUtils
                    .getPropertyDescriptor(objValidacao.getClass(), descricaoField)
                    .getReadMethod()
                    .invoke(objValidacao);

            if (valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && descricao != null) {
                valido = descricao.toLowerCase().contains(this.descricaoObrigatoria.toLowerCase());
            }

            return valido;

        } catch (final Exception e) {
            throw new ValidationException(e);
        }
    }

}
