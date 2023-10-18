package com.equipo10.proyectointegrador.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<String> processResourceNotFound(ResourceNotFoundException resourceNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resourceNotFoundException.getMessage());
    }

    @ExceptionHandler({ResourceAlreadyExistExeption.class})
    public ResponseEntity<String> processUserAlreadyExist(ResourceAlreadyExistExeption resourceAlreadyExistExeption) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(resourceAlreadyExistExeption.getMessage());
    }

}