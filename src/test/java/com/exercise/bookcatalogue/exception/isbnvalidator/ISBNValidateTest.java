package com.exercise.bookcatalogue.exception.isbnvalidator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ISBNValidateTest {
    ISBNValidate validator;

    @BeforeEach
    void startUp() {
        validator = new ISBNValidate();
    }

    @Test
    @DisplayName("Returns true for a valid ISBN number")
    public void checkAValidIsbn() {
        assertTrue(validator.checkIsbn("9992158107"));
        assertTrue(validator.checkIsbn("012000030X"));
        assertTrue(validator.checkIsbn("9781449357672"));
    }

    @Test
    @DisplayName("Returns false for an invalid ISBN number")
    public void checkAnInValidIsbn() {
        assertFalse(validator.checkIsbn("9992158108"));
    }
}