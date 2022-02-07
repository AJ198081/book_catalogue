package com.exercise.bookcatalogue.exception;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class ValidationError {
    public void inputValidationErrors(BindingResult errors) {
        List<String> message = new ArrayList<>();
        for (FieldError error : errors.getFieldErrors()) {
            message.add("#" + error.getField().toUpperCase(Locale.ROOT) + " : " + error.getDefaultMessage());
        }
        throw new ExerciseCustomException(String.format("Request parameters have violated %d constraint(s), -> " +
                "{%s}", errors.getErrorCount(), message));
    }
}
