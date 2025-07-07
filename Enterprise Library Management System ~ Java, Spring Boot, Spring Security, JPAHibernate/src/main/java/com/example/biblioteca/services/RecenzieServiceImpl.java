package com.example.biblioteca.services;

import com.example.biblioteca.exception.AutorNotFoundException;
import com.example.biblioteca.exception.RecenzieNotFoundException;
import com.example.biblioteca.model.Autor;
import com.example.biblioteca.model.Recenzie;
import com.example.biblioteca.repositories.RecenzieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecenzieServiceImpl implements RecenzieService{

    private final RecenzieRepository recenzieRepository;

    public RecenzieServiceImpl(RecenzieRepository recenzieRepository) {
        this.recenzieRepository = recenzieRepository;
    }


    public Recenzie saveRecenzie(Recenzie recenzie) {
        return recenzieRepository.save(recenzie);
    }

    public List<Recenzie> getRecenzieAscCarte() {
        return recenzieRepository.findAllByOrderByCarteAsc();
    }

    public List<Recenzie> getRecenzieAscCititor() {
        return recenzieRepository.findAllByOrderByCititorAsc();
    }

    public List<Recenzie> getAllRecenzie() {
        return recenzieRepository.findAll();
    }

    public void deleteById(Long id) {

        Optional<Recenzie> recenzie = recenzieRepository.findById(id);
        if(recenzie.isPresent()){
            recenzieRepository.deleteById(id);
        }else
        {
            throw new RecenzieNotFoundException(id);
        }

    }
}
