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
        int total = 0;
        boolean valid = false;
        if (isbn.length() == 10 || isbn.length() == 13) {
            for (int i = 0; i < isbn.length(); i++) {
                if (!Character.isDigit(isbn.charAt(i))) {
                    final int lastCharacter = isbn.length() - 1;
                    if (i == lastCharacter && isbn.charAt(lastCharacter) == 'X') {
                        total += 10;
                    } else {
                        System.out.println("ISBN needs to be number.");
                    }
                } else {
                    total += Character.getNumericValue(isbn.charAt(i)) * (10 - i);
                }
            }
            valid = total % 11 == 0;
        } else {
            throw new NumberFormatException("ISBN needs to be either 10 or 13 digits long.");
        }
        return valid;
    }
}
