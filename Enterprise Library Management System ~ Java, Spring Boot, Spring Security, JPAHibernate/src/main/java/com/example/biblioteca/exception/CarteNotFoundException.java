package com.example.biblioteca.exception;

public class CarteNotFoundException extends RuntimeException {

    public CarteNotFoundException(long id){
        super("Cartea cu id " + id + " nu exista!");
    }
}
