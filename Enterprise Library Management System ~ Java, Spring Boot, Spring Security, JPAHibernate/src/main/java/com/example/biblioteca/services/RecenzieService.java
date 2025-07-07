package com.example.biblioteca.services;

import com.example.biblioteca.model.Carte;
import com.example.biblioteca.model.Recenzie;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RecenzieService {

    Recenzie saveRecenzie(Recenzie recenzie);
    List<Recenzie> getRecenzieAscCarte();

    List<Recenzie> getRecenzieAscCititor();
    List<Recenzie> getAllRecenzie();
    void deleteById(Long id);
}
