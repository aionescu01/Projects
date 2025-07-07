package com.example.biblioteca.exception;

public class CititorNotFoundException extends RuntimeException{

    public CititorNotFoundException(long id){
        super("Cititorul cu id " + id + " nu exista!");
    }

}
