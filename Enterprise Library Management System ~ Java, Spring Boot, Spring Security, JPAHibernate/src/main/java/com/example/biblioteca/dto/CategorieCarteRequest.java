package com.example.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;

public class CategorieCarteRequest {

    @NotBlank(message = "Numele nu poate fi null")
    private String nume;


    public CategorieCarteRequest() {
    }

    public CategorieCarteRequest(String nume) {
        this.nume = nume;
    }


    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }
}
