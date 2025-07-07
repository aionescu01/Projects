package com.example.biblioteca.services;

import com.example.biblioteca.model.Cititor;

import java.util.List;

public interface CititorService {

    Cititor saveCititor(Cititor cititor);
    List<Cititor> getCititorAscNume();

    List<Cititor> getAllCititor();

    void deleteById(Long id);
}
