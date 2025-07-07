package com.example.biblioteca.dto;

import com.example.biblioteca.model.Carte;
import com.example.biblioteca.model.Cititor;
import jakarta.validation.constraints.NotNull;


import java.util.Date;

public class ImprumutRequest {

    @NotNull(message = "Data de imprumut nu poate fi null")
    private Date data_imprumut;

    private Date data_returnare;


    @NotNull(message = "Cartea nu poate fi null")
    private Carte carte;

    @NotNull(message = "Cititorul nu poate fi null")
    private Cititor cititor;


    public ImprumutRequest() {
    }

    public ImprumutRequest(Date data_imprumut, Date data_returnare, Carte carte, Cititor cititor) {
        this.data_imprumut = data_imprumut;
        this.data_returnare = data_returnare;
        this.carte = carte;
        this.cititor = cititor;
    }

    public Date getData_imprumut() {
        return data_imprumut;
    }

    public void setData_imprumut(Date data_imprumut) {
        this.data_imprumut = data_imprumut;
    }

    public Date getData_returnare() {
        return data_returnare;
    }

    public void setData_returnare(Date data_returnare) {
        this.data_returnare = data_returnare;
    }

    public Carte getCarte() {
        return carte;
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    public Cititor getCititor() {
        return cititor;
    }

    public void setCititor(Cititor cititor) {
        this.cititor = cititor;
    }
}
