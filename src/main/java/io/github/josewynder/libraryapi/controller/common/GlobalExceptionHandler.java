package io.github.josewynder.libraryapi.controller.common;

import io.github.josewynder.libraryapi.controller.dto.ErrorField;
import io.github.josewynder.libraryapi.controller.dto.ResponseError;
import io.github.josewynder.libraryapi.exceptions.DuplicateRegistrationException;
import io.github.josewynder.libraryapi.exceptions.InvalidFieldException;
import io.github.josewynder.libraryapi.exceptions.OperationNotPermittedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseError handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Validation error: {}", e.getMessage());
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErrorField> errorList = fieldErrors
                .stream()
                .map(fe -> new ErrorField(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ResponseError(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation Error",
                errorList);
    }

    @ExceptionHandler(DuplicateRegistrationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseError handleDuplicateRegistrationException(DuplicateRegistrationException e) {
        return ResponseError.conflict(e.getMessage());
    }

    @ExceptionHandler(OperationNotPermittedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleOperationNotPermittedException(OperationNotPermittedException e) {
        return ResponseError.defaultAnswer(e.getMessage());
    }

    @ExceptionHandler(InvalidFieldException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseError handleInvalidFieldException(InvalidFieldException e) {
        return new ResponseError(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation error",
                List.of(new ErrorField(e.getFieldName(), e.getMessage())));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseError handleAccessDeniedException(AccessDeniedException e) {
        return new ResponseError(
                HttpStatus.FORBIDDEN.value(),
                "Access denied",
                List.of());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseError handleUnhandledErrors(RuntimeException e) {
        log.error("Unexpected error", e);
        return new ResponseError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred. Please contact management.",
                List.of());
    }
}
