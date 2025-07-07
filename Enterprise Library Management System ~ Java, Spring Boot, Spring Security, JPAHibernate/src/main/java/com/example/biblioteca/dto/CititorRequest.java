package com.example.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Date;

public class CititorRequest {

    @NotBlank(message = "Numele nu poate fi null")
    private String nume;

    @NotBlank(message = "Prenumele nu poate fi null")
    private String prenume;

    private String email;

    @NotBlank(message = "Numarul de telefon nu poate fi null")
    private String nr_telefon;

    @NotNull(message = "CNP-ul nu poate fi null")
    @Pattern(regexp = "\\d{13}", message = "CNP incorect")
    private String CNP;

    private Date data_nasterii;

    public CititorRequest() {
    }

    public CititorRequest(String nume, String prenume, String email, String nr_telefon, String CNP, Date data_nasterii) {
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.nr_telefon = nr_telefon;
        this.CNP = CNP;
        this.data_nasterii = data_nasterii;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNr_telefon() {
        return nr_telefon;
    }

    public void setNr_telefon(String nr_telefon) {
        this.nr_telefon = nr_telefon;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public Date getData_nasterii() {
        return data_nasterii;
    }

    public void setData_nasterii(Date data_nasterii) {
        this.data_nasterii = data_nasterii;
    }
}
