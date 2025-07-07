package com.example.biblioteca.services;

import com.example.biblioteca.model.Autor;
import com.example.biblioteca.model.Carte;

import java.util.List;

public interface CarteService {
    Carte saveCarte(Carte carte);
    List<Carte> getCarteAscNume();

    void deleteById(Long id);

    List<Carte> getAllCarte();
}
