package com.murilonerdx.epictask.handler;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code= HttpStatus.BAD_REQUEST)
    public List<ValidationFieldError> handle(MethodArgumentNotValidException e){
        List<ValidationFieldError> list = new ArrayList<>();
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        errors.forEach(erro->{
            list.add(new ValidationFieldError(erro.getField(), erro.getDefaultMessage()));
        });
        return list;
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ValidationFieldError> handleAllExceptions(Exception ex, WebRequest request) {
        ValidationFieldError exceptionResponse =
                new ValidationFieldError(
                        ex.getMessage(),
                        request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ValidationFieldError> handleBadRequestExceptions(Exception ex, WebRequest request) {
        ValidationFieldError exceptionResponse =
                new ValidationFieldError(
                        ex.getMessage(),
                        request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
