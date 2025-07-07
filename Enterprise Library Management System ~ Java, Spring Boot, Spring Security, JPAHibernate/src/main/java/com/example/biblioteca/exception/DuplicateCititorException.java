package com.example.biblioteca.exception;

public class DuplicateCititorException extends RuntimeException{

    public DuplicateCititorException(){
        super("Exista un cititor cu acest CNP!");
    }
}
