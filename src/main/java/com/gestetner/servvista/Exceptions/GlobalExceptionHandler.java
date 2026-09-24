package com.gestetner.servvista.Exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleNotFound(EntityNotFoundException exception) {
        return problem(HttpStatus.NOT_FOUND, "Resource not found", exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleBadRequest(IllegalArgumentException exception) {
        return problem(HttpStatus.BAD_REQUEST, "Invalid request", exception.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ProblemDetail handleConflict(IllegalStateException exception) {
        return problem(HttpStatus.CONFLICT, "Resource conflict", exception.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail handleBadCredentials(BadCredentialsException exception) {
        return problem(HttpStatus.UNAUTHORIZED, "Authentication failed", exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                errors.putIfAbsent(error.getField(), error.getDefaultMessage()));

        ProblemDetail problemDetail = problem(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                "One or more request fields are invalid");
        problemDetail.setProperty("errors", errors);
        return problemDetail;
    }

    private ProblemDetail problem(HttpStatus status, String title, String detail) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setTitle(title);
        return problemDetail;
    }
}
