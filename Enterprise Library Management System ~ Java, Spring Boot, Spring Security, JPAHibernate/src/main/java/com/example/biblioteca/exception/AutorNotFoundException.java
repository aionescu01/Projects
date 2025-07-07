package com.example.biblioteca.exception;

public class AutorNotFoundException extends RuntimeException{

    public AutorNotFoundException(long id){
        super("Autorul cu id " + id + " nu exista!");
    }

}
