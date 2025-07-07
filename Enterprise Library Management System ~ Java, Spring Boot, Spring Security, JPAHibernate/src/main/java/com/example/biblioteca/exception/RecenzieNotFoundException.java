package com.example.biblioteca.exception;

public class RecenzieNotFoundException extends RuntimeException{

    public RecenzieNotFoundException(long id){
        super("Recenzia cu id " + id + " nu exista!");
    }
}
