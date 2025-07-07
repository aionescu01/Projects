package com.example.biblioteca.services;

import com.example.biblioteca.model.Carte;
import com.example.biblioteca.model.CategorieCarte;

import java.util.List;

public interface CategorieCarteService {

    CategorieCarte saveCategorieCarte(CategorieCarte categorieCarte);
    List<CategorieCarte> getCategorieCarteAscNume();
    void deleteById(Long id);
    List<CategorieCarte> getAllCategorieCarte();
}
