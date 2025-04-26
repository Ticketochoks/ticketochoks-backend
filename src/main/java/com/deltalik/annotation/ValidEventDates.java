package com.deltalik.annotation;

import com.deltalik.validator.EventDatesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EventDatesValidator.class)
public @interface ValidEventDates {
  String message() default "Start date/time must be before end date/time and in correct format";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
