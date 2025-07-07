package com.example.biblioteca.exception;

public class ImprumutNotFoundException extends RuntimeException{

    public ImprumutNotFoundException(long id){
        super("Imprumutul cu id " + id + " nu exista!");
    }
}
