package com.algaworks.algafood.core.validation;

import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

    private DataSize maxSize;

    @Override
    public void initialize(final FileSize constraintAnnotation) {
        this.maxSize = DataSize.parse(constraintAnnotation.max());
    }

    @Override
    public boolean isValid(final MultipartFile value,
                           final ConstraintValidatorContext context) {

        if (value == null || value.getSize() > this.maxSize.toBytes()) {
            return false;
        }

        return true;
    }

}
