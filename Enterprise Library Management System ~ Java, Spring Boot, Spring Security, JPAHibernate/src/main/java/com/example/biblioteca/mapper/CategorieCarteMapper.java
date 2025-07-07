package com.example.biblioteca.mapper;

import com.example.biblioteca.dto.CategorieCarteRequest;
import com.example.biblioteca.model.CategorieCarte;
import org.springframework.stereotype.Component;


@Component
public class CategorieCarteMapper {

    public CategorieCarte categorieCarteRequestToCategorieCarte(CategorieCarteRequest categorieCarteRequest){
        return new CategorieCarte(categorieCarteRequest.getNume());
    }
}
