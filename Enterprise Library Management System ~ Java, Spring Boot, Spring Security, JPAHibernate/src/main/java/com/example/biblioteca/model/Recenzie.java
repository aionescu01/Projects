package com.example.biblioteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
public class Recenzie {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //@NotNull
    private int rating;

    private String review;

    private Date data_recenzie;

    @ManyToOne
    @JoinColumn(name = "cititor")
    private Cititor cititor;

    @ManyToOne
    @JoinColumn(name = "carte")
    private Carte carte;

    public Recenzie() {
    }

    public Recenzie(long id, int rating, String review, Date data_recenzie, Cititor cititor, Carte carte) {
        this.id = id;
        this.rating = rating;
        this.review = review;
        this.data_recenzie = data_recenzie;
        this.cititor = cititor;
        this.carte = carte;
    }

    public Recenzie(int rating, String review, Date data_recenzie, Cititor cititor, Carte carte) {
        this.rating = rating;
        this.review = review;
        this.data_recenzie = data_recenzie;
        this.cititor = cititor;
        this.carte = carte;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
