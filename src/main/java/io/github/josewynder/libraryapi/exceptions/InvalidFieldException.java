package io.github.josewynder.libraryapi.exceptions;

import lombok.Getter;

public class InvalidFieldException extends RuntimeException {

    @Getter
    private String fieldName;

    public InvalidFieldException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }
}
