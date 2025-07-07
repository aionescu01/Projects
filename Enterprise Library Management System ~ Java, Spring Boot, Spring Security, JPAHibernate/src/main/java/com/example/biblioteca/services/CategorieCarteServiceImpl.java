package com.example.biblioteca.services;

import com.example.biblioteca.exception.AutorNotFoundException;
import com.example.biblioteca.exception.CategorieCarteNotFoundException;
import com.example.biblioteca.model.Autor;
import com.example.biblioteca.model.CategorieCarte;
import com.example.biblioteca.repositories.CategorieCarteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategorieCarteServiceImpl implements CategorieCarteService{

    private final CategorieCarteRepository categorieCarteRepository;

    public CategorieCarteServiceImpl(CategorieCarteRepository categorieCarteRepository) {
        this.categorieCarteRepository = categorieCarteRepository;
    }

    public CategorieCarte saveCategorieCarte(CategorieCarte categorieCarte) {
        return categorieCarteRepository.save(categorieCarte);
    }


    public List<CategorieCarte> getCategorieCarteAscNume() {
        return categorieCarteRepository.findAllByOrderByNumeAsc();
    }

    public void deleteById(Long id) {

        Optional<CategorieCarte> categorieCarte = categorieCarteRepository.findById(id);
        if(categorieCarte.isPresent()){
            categorieCarteRepository.deleteById(id);
        }else
        {
            throw new CategorieCarteNotFoundException(id);
        }

    }

    public List<CategorieCarte> getAllCategorieCarte() {
        return categorieCarteRepository.findAll();
    }
}
