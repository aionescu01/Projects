package com.example.biblioteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Objects;

@Entity
public class CategorieCarte {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //@NotNull
    private String nume;



    public CategorieCarte() {
    }

    public CategorieCarte(long id, String nume) {
        this.id = id;
        this.nume = nume;
    }

    public CategorieCarte(String nume) {
        this.nume = nume;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategorieCarte that = (CategorieCarte) o;

        return Objects.equals(nume, that.nume);
    }

    @Override
    public int hashCode() {
        return nume != null ? nume.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CategorieCarte{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                '}';
    }
}
