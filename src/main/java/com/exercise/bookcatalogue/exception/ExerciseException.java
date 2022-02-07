package com.exercise.bookcatalogue.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ExerciseException {
    private final String message;
    private final LocalDateTime localDateTime;
    private final HttpStatus httpStatus;

    public ExerciseException(String message, LocalDateTime localDateTime, HttpStatus httpStatus) {
        this.message = message;
        this.localDateTime = localDateTime;
        this.httpStatus = httpStatus;
    }
}
