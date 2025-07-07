package com.example.biblioteca.exception;

public class CategorieCarteNotFoundException extends RuntimeException{

    public CategorieCarteNotFoundException(long id){
        super("Categoria de carte cu id " + id + " nu exista!");
    }
}
