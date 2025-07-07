package com.example.biblioteca.mapper;

import com.example.biblioteca.dto.CarteRequest;
import com.example.biblioteca.model.Carte;
import org.springframework.stereotype.Component;

@Component
public class CarteMapper {

    public Carte carteRequestToCarte(CarteRequest carteRequest){
        return new Carte(carteRequest.getTitlu(),carteRequest.getData_publicare(),carteRequest.getAutor(),carteRequest.getCategorie());
    }

}
