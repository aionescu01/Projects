package com.example.biblioteca.services;

import com.example.biblioteca.model.Autor;
import com.example.biblioteca.model.Carte;

import java.util.List;

public interface AutorService {

    Autor saveAutor(Autor autor);
    List<Autor> getAutorAscNume();

    List<Autor> getAllAutor();

    void deleteById(Long id);
}
