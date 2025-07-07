package com.example.biblioteca.dto;

import com.example.biblioteca.model.Autor;
import com.example.biblioteca.model.Carte;
import com.example.biblioteca.model.CategorieCarte;
import com.example.biblioteca.model.Cititor;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class RecenzieRequest {

    @NotNull(message = "Ratingul nu poate fi null")
    private int rating;

    private String review;

    private Date data_recenzie;

    @NotNull(message = "Cititorul nu poate fi null")
    private Cititor cititor;

    @NotNull(message = "Cartea nu poate fi null")
    private Carte carte;


    public RecenzieRequest() {
    }

    public RecenzieRequest(int rating, String review, Date data_recenzie, Cititor cititor, Carte carte) {
        this.rating = rating;
        this.review = review;
        this.data_recenzie = data_recenzie;
        this.cititor = cititor;
        this.carte = carte;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Date getData_recenzie() {
        return data_recenzie;
    }

    public void setData_recenzie(Date data_recenzie) {
        this.data_recenzie = data_recenzie;
    }

    public Cititor getCititor() {
        return cititor;
    }

    public void setCititor(Cititor cititor) {
        this.cititor = cititor;
    }

    public Carte getCarte() {
        return carte;
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }
}
