package ru.project.controller;

import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.project.exception.NotEnoughAmountException;
import ru.project.exception.ResourceNotFoundException;

public class ExceptionApiHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> ResourceNotFoundException(ResourceNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(NotEnoughAmountException.class)
    public ResponseEntity<ErrorMessage> NotEnoughAmountException(NotEnoughAmountException exception) {
        return ResponseEntity
                .status(HttpStatus.PAYMENT_REQUIRED)  //TODO change
                .body(new ErrorMessage(exception.getMessage()));
    }
}
