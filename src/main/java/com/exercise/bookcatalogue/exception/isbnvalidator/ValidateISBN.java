package com.exercise.bookcatalogue.exception.isbnvalidator;

import org.hibernate.validator.constraints.ISBN;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidateISBN implements ConstraintValidator<IsbnValidator, String> {
    @Override
    public void initialize(IsbnValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext constraintValidatorContext) {
        ISBNValidate validate = new ISBNValidate();
        return validate.checkIsbn(isbn);
    }
}
