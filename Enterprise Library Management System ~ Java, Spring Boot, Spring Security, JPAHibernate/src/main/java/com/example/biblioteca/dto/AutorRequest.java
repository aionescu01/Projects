package com.example.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AutorRequest {

    @NotBlank(message = "Numele nu poate fi null")
    @Pattern(regexp = "[a-zA-z\\s.-]+", message = "Numele contine caractere nepermise")
    private String nume;
    @NotBlank(message = "Prenumele nu poate fi null")
    @Pattern(regexp = "[a-zA-z\\s.-]+", message = "Prenumele contine caractere nepermise")
    private String prenume;


    public AutorRequest() {
    }

    public AutorRequest(String nume, String prenume) {
        this.nume = nume;
        this.prenume = prenume;
    }



    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }
}
