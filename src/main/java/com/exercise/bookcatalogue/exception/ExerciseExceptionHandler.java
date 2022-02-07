package com.exercise.bookcatalogue.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Set;

@ControllerAdvice
public class ExerciseExceptionHandler {

    @ExceptionHandler(value = {ExerciseCustomException.class})
    public ResponseEntity<Object> handleConstraintViolation(ExerciseCustomException exception) {
        ExerciseException exerciseException = new ExerciseException(exception.getMessage(), LocalDateTime.now(),
                HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(exerciseException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> handleCVException(SQLIntegrityConstraintViolationException exception) {
        String violation = exception.getMessage();
        System.out.println(violation);
        return new ResponseEntity<>(violation.substring(0, violation.indexOf(";")),HttpStatus.CONFLICT);
    }
}
