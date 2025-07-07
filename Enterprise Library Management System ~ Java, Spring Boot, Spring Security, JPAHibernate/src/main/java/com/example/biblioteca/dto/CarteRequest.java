package com.example.biblioteca.dto;

import com.example.biblioteca.model.Autor;
import com.example.biblioteca.model.CategorieCarte;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

public class CarteRequest {

    @NotBlank(message = "Titlul nu poate fi null")
    private String titlu;

    private Date data_publicare;

    @NotNull(message = "Autorul nu poate fi null")
    private Autor autor;


    private CategorieCarte categorie;


    public CarteRequest() {
    }

    public CarteRequest(String titlu, Date data_publicare, Autor autor, CategorieCarte categorie) {
        this.titlu = titlu;
        this.data_publicare = data_publicare;
        this.autor = autor;
        this.categorie = categorie;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public Date getData_publicare() {
        return data_publicare;
    }

    public void setData_publicare(Date data_publicare) {
        this.data_publicare = data_publicare;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public CategorieCarte getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieCarte categorie) {
        this.categorie = categorie;
    }
}
