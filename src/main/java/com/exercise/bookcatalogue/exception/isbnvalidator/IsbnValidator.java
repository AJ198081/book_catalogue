package com.exercise.bookcatalogue.exception.isbnvalidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = ValidateISBN.class)
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IsbnValidator {
    String message() default "{ISBN is a 10 or 13 digit number}";

    Class<?> [] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
