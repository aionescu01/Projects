package com.example.biblioteca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Objects;

@Entity
public class Autor {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    //@Pattern(regexp = "[a-zA-z\\s.-]+", message = "Numele contine caractere nepermise")
    private String nume;
    @NotNull
    //@Pattern(regexp = "[a-zA-z\\s.-]+", message = "Prenumele contine caractere nepermise")
    private String prenume;

    public Autor() {
    }



    public Autor(long id, String nume, String prenume) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
    }

    public Autor(String nume, String prenume) {
        this.nume = nume;
        this.prenume = prenume;
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

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Autor autor = (Autor) o;

        if (!Objects.equals(nume, autor.nume)) return false;
        return Objects.equals(prenume, autor.prenume);
    }

    @Override
    public int hashCode() {
        int result = nume != null ? nume.hashCode() : 0;
        result = 31 * result + (prenume != null ? prenume.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                '}';
    }
}
