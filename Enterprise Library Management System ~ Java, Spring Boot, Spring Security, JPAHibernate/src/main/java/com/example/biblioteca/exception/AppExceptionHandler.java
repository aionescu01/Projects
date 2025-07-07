package com.example.biblioteca.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler({ImprumutNotFoundException.class})
    public ResponseEntity handle(ImprumutNotFoundException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({DuplicateCititorException.class})
    public ResponseEntity handle(DuplicateCititorException e){
        return ResponseEntity.status(500).body(e.getMessage());
    }

    @ExceptionHandler({AutorNotFoundException.class})
    public ResponseEntity handle(AutorNotFoundException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({CarteNotFoundException.class})
    public ResponseEntity handle(CarteNotFoundException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({CategorieCarteNotFoundException.class})
    public ResponseEntity handle(CategorieCarteNotFoundException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({CititorNotFoundException.class})
    public ResponseEntity handle(CititorNotFoundException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({RecenzieNotFoundException.class})
    public ResponseEntity handle(RecenzieNotFoundException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }


    //pentru validare fielduri
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new LinkedHashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            errors.put(((FieldError) error).getField(), error.getDefaultMessage());
        });

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp: ", Instant.now());
        body.put("status: ", HttpStatus.BAD_REQUEST.value());
        body.put("errors: ", errors);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
