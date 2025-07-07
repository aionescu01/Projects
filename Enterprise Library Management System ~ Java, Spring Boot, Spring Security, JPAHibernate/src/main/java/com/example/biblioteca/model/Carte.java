package com.example.biblioteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity

public class Carte {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //@NotNull
    private String titlu;

    private Date data_publicare;

    @ManyToOne
    @JoinColumn(name = "autor")
    private Autor autor;

    @ManyToOne
    @JoinColumn(name = "categorie")
    private CategorieCarte categorie;




    public Carte() {
    }

    public Carte(long id, String titlu, Autor autor, CategorieCarte categorie) {
        this.id = id;
        this.titlu = titlu;
        this.autor = autor;
        this.categorie = categorie;
    }

    public Carte(long id, String titlu, Date data_publicare, Autor autor, CategorieCarte categorie) {
        this.id = id;
        this.titlu = titlu;
        this.data_publicare = data_publicare;
        this.autor = autor;
        this.categorie = categorie;
    }

    public Carte(String titlu, Date data_publicare, Autor autor, CategorieCarte categorie) {
        this.titlu = titlu;
        this.data_publicare = data_publicare;
        this.autor = autor;
        this.categorie = categorie;
    }

    public Carte(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Carte carte = (Carte) o;

        if (!Objects.equals(titlu, carte.titlu)) return false;
        if (!Objects.equals(data_publicare, carte.data_publicare))
            return false;
        if (!Objects.equals(autor, carte.autor)) return false;
        return Objects.equals(categorie, carte.categorie);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (titlu != null ? titlu.hashCode() : 0);
        result = 31 * result + (data_publicare != null ? data_publicare.hashCode() : 0);
        result = 31 * result + (autor != null ? autor.hashCode() : 0);
        result = 31 * result + (categorie != null ? categorie.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Carte{" +
                "id=" + id +
                ", titlu='" + titlu + '\'' +
                ", data_publicare=" + data_publicare +
                ", autor=" + autor +
                ", categorie=" + categorie +
                '}';
    }
}
