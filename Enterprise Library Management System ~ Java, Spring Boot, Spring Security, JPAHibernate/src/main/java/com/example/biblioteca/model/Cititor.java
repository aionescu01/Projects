package com.example.biblioteca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Date;
import java.util.Objects;

@Entity
public class Cititor {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nume;

    private String prenume;

    private String email;

    private String nr_telefon;

    @NotNull
    @Pattern(regexp = "\\d{13}", message = "CNP incorect")
    private String CNP;

    private Date data_nasterii;

    public Cititor() {
    }

    public Cititor(long id, String nume, String prenume, String email, String nr_telefon, String CNP, Date data_nasterii) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.nr_telefon = nr_telefon;
        this.CNP = CNP;
        this.data_nasterii = data_nasterii;
    }

    public Cititor(String nume, String prenume, String email, String nr_telefon, String CNP, Date data_nasterii) {
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.nr_telefon = nr_telefon;
        this.CNP = CNP;
        this.data_nasterii = data_nasterii;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cititor cititor = (Cititor) o;

        if (!Objects.equals(nume, cititor.nume)) return false;
        if (!Objects.equals(prenume, cititor.prenume)) return false;
        if (!Objects.equals(email, cititor.email)) return false;
        if (!Objects.equals(nr_telefon, cititor.nr_telefon)) return false;
        if (!Objects.equals(CNP, cititor.CNP)) return false;
        return Objects.equals(data_nasterii, cititor.data_nasterii);
    }

    @Override
    public int hashCode() {
        int result = nume != null ? nume.hashCode() : 0;
        result = 31 * result + (prenume != null ? prenume.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (nr_telefon != null ? nr_telefon.hashCode() : 0);
        result = 31 * result + (CNP != null ? CNP.hashCode() : 0);
        result = 31 * result + (data_nasterii != null ? data_nasterii.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Cititor{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", email='" + email + '\'' +
                ", nr_telefon='" + nr_telefon + '\'' +
                ", CNP='" + CNP + '\'' +
                ", data_nasterii=" + data_nasterii +
                '}';
    }
}
