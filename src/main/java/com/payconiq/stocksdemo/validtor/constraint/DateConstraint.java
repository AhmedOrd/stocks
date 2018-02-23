package com.payconiq.stocksdemo.validtor.constraint;


import com.payconiq.stocksdemo.validtor.DateValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Constraint(validatedBy = { DateValidator.class })
@Documented
@Target({
          ElementType.METHOD,
          ElementType.FIELD,
          ElementType.ANNOTATION_TYPE,
          ElementType.CONSTRUCTOR,
          ElementType.PARAMETER })

@Retention(RetentionPolicy.RUNTIME)
public @interface DateConstraint {

    String message() default "date.invalid.error.reason";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
