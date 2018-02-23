package com.payconiq.stocksdemo.validtor;

import java.lang.annotation.Annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public abstract class NonNullValidator<A extends Annotation, T> implements ConstraintValidator<A, T> {

    @Override
    public boolean isValid(T value, ConstraintValidatorContext context) {
        return value == null || isNonNullValid(value, context);

    }

    protected abstract boolean isNonNullValid(T value, ConstraintValidatorContext context);

}
