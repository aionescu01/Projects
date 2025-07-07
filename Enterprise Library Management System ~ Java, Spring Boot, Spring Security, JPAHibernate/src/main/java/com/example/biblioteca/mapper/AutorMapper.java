package com.example.biblioteca.mapper;

import com.example.biblioteca.dto.AutorRequest;
import com.example.biblioteca.dto.CarteRequest;
import com.example.biblioteca.model.Autor;
import com.example.biblioteca.model.Carte;
import org.springframework.stereotype.Component;

@Component
public class AutorMapper {

    public Autor autorRequestToAutor(AutorRequest autorRequest){
        return new Autor(autorRequest.getNume(), autorRequest.getPrenume());
    }
}
