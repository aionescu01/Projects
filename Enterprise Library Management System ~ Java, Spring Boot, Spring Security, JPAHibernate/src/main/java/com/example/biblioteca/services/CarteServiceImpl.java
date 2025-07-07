package com.example.biblioteca.services;

import com.example.biblioteca.exception.AutorNotFoundException;
import com.example.biblioteca.exception.CarteNotFoundException;
import com.example.biblioteca.model.Autor;
import com.example.biblioteca.model.Carte;
import com.example.biblioteca.repositories.CarteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarteServiceImpl implements CarteService{

    private final CarteRepository carteRepository;

    public CarteServiceImpl(CarteRepository carteRepository) {
        this.carteRepository = carteRepository;
    }

    public Carte saveCarte(Carte carte) {
        return carteRepository.save(carte);
    }

    public List<Carte> getCarteAscNume(){
        return carteRepository.findAllByOrderByTitluAsc();
    }

    public List<Carte> getAllCarte() {
        return carteRepository.findAll();
    }

    public void deleteById(Long id) {

        Optional<Carte> carte = carteRepository.findById(id);
        if(carte.isPresent()){
            carteRepository.deleteById(id);
        }else
        {
            throw new CarteNotFoundException(id);
        }

    }
}
