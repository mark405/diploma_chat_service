package com.diploma.chat.controllers;

import com.diploma.chat.exceptions.NotFoundException;
import com.diploma.chat.exceptions.messages.ErrorDetailsMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDetailsMessage> handleGlobalException(NotFoundException ex) {
        return new ResponseEntity<>(new ErrorDetailsMessage(ex.getMessage()), HttpStatus.NOT_FOUND);
    }
}
