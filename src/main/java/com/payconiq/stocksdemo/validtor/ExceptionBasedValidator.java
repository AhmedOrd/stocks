package com.payconiq.stocksdemo.validtor;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ExceptionBasedValidator {

    private final Validator validator;

    @Autowired
    public ExceptionBasedValidator(Validator validator) {
        this.validator = validator;
    }

    public void assertObjectsAreValid(Object... subjects) {
        for (Object subject : subjects) {
            assertObjectIsValid(subject);
        }
    }

    private <T> void assertObjectIsValid(T subject) {
        Set<ConstraintViolation<T>> violations = validator.validate(subject);
        if (!violations.isEmpty()) {
            log.debug("Number of violations: [{}]", violations.size());
            throw new ConstraintViolationException(violations);
        }
    }
}
