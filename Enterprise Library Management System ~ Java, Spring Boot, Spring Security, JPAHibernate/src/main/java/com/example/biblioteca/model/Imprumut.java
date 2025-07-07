package com.example.biblioteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.Objects;

@Entity
public class Imprumut {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date data_imprumut;

    private Date data_returnare;

    @ManyToOne
    @JoinColumn(name = "carte")
    private Carte carte;

    @ManyToOne
    @JoinColumn(name = "cititor")
    private Cititor cititor;

    public Imprumut() {
    }

    public Imprumut(long id, Date data_imprumut, Date data_returnare, Carte carte, Cititor cititor) {
        this.id = id;
        this.data_imprumut = data_imprumut;
        this.data_returnare = data_returnare;
        this.carte = carte;
        this.cititor = cititor;
    }

    public Imprumut(Date data_imprumut, Date data_returnare, Carte carte, Cititor cititor) {
        this.data_imprumut = data_imprumut;
        this.data_returnare = data_returnare;
        this.carte = carte;
        this.cititor = cititor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Imprumut imprumut = (Imprumut) o;

        if (!data_imprumut.equals(imprumut.data_imprumut)) return false;
        if (!Objects.equals(data_returnare, imprumut.data_returnare))
            return false;
        if (!carte.equals(imprumut.carte)) return false;
        return cititor.equals(imprumut.cititor);
    }

    @Override
    public int hashCode() {
        int result = data_imprumut.hashCode();
        result = 31 * result + (data_returnare != null ? data_returnare.hashCode() : 0);
        result = 31 * result + carte.hashCode();
        result = 31 * result + cititor.hashCode();
        return result;
    }
}
