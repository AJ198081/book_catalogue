package com.exercise.bookcatalogue.exception;

public class ExerciseCustomException extends RuntimeException{

    public ExerciseCustomException(String message) {
        super(message);
    }

    public ExerciseCustomException(Throwable cause) {
        super(cause);
    }
}
