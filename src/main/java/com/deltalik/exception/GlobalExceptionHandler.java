package com.deltalik.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(DuplicateResourceException.class)
  public ResponseEntity<ErrorResponse> handleDuplicateResourceException(
      DuplicateResourceException ex) {
    HttpStatus conflict = HttpStatus.CONFLICT;
    return new ResponseEntity<>(
        new ErrorResponse(conflict.value(), ex.getMessage(), LocalDateTime.now()), conflict);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFoundException(ResourceNotFoundException ex) {
    HttpStatus conflict = HttpStatus.NOT_FOUND;
    return new ResponseEntity<>(
        new ErrorResponse(conflict.value(), ex.getMessage(), LocalDateTime.now()), conflict);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrorResponse> handleValidationException(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(error -> {
          String field = ((FieldError) error).getField();
          String message = error.getDefaultMessage();
          errors.put(field, message);
        });
    HttpStatus badRequest = HttpStatus.BAD_REQUEST;
    return new ResponseEntity<>(
        new ValidationErrorResponse(badRequest.value(), "Validation failed", errors,
            LocalDateTime.now()),
        badRequest);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handleMethodNotSupported(
      HttpRequestMethodNotSupportedException ex) {
    HttpStatus methodNotAllowed = HttpStatus.METHOD_NOT_ALLOWED;
    assert ex.getSupportedMethods() != null;
    String supported = String.join(", ", ex.getSupportedMethods());
    String message = "Method " + ex.getMethod() + " not supported. Supported: " + supported;
    return new ResponseEntity<>(
        new ErrorResponse(methodNotAllowed.value(), message, LocalDateTime.now()),
        methodNotAllowed);
  }
}
