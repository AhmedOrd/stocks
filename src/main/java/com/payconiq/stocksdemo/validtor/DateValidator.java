package com.payconiq.stocksdemo.validtor;

import java.time.LocalDateTime;

import javax.validation.ConstraintValidatorContext;

import com.payconiq.stocksdemo.validtor.constraint.DateConstraint;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DateValidator extends NonNullValidator<DateConstraint, LocalDateTime> {

    @Override
    public void initialize(DateConstraint ibanConstraint) {
        // Not doing anything with the annotation specifically
    }

    @Override
    protected boolean isNonNullValid(LocalDateTime date, ConstraintValidatorContext constraintValidatorContext) {

        boolean isValid = date != null//
                && isValid(date);
        log.debug("Validated date [{}]: {}", date, isValid);
        return isValid;

    }

    private boolean isValid(LocalDateTime date) {
        return true; //And here we can validate the date, but for the assignment i will do nothing.
    }

}
