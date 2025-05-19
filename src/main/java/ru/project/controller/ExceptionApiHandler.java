package ru.project.controller;

import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.project.exception.*;

@RestControllerAdvice
public class ExceptionApiHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> ResourceNotFoundException(ResourceNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage("Кошелек не найден " + exception.getMessage()));
    }

    @ExceptionHandler(NotEnoughAmountException.class)
    public ResponseEntity<ErrorMessage> NotEnoughAmountException(NotEnoughAmountException exception) {
        return ResponseEntity
                .status(HttpStatus.PAYMENT_REQUIRED)
                .body(new ErrorMessage("Недостаточно средств для выполнения операции. Текущий баланс: "+ exception.getMessage()));
    }
    @ExceptionHandler(UUIDFormatException.class)
    public ResponseEntity<ErrorMessage> UUIDFormatException(UUIDFormatException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage("Ошибка формата " + exception.getMessage()));
    }
    @ExceptionHandler(IncorrectFormatDataException.class)
    public ResponseEntity<ErrorMessage> IncorrectFormatDataException(IncorrectFormatDataException exception) {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new ErrorMessage("Ошибка ввода данных " + exception.getMessage()));
    }
//    @ExceptionHandler(JSONFormatException.class)
//    public ResponseEntity<ErrorMessage> JSONFormatException(JSONFormatException exception) {
//        return ResponseEntity
//                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
//                .body(new ErrorMessage("Ошибка JSON формата " + exception.getMessage()));
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> DifferentException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(new ErrorMessage("Ошибка входящих данных. Не корректный JSON " + exception.getMessage()));
    }
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorMessage> DifferentGeneralException(Throwable exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessage("Ошибка сервиса " + exception.getMessage()));
    }



}
