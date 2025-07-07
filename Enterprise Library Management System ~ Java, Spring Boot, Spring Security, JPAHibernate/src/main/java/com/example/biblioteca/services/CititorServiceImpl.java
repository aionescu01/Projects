package com.example.biblioteca.services;

import com.example.biblioteca.exception.AutorNotFoundException;
import com.example.biblioteca.exception.CititorNotFoundException;
import com.example.biblioteca.exception.DuplicateCititorException;
import com.example.biblioteca.model.Autor;
import com.example.biblioteca.model.Cititor;
import com.example.biblioteca.repositories.CititorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CititorServiceImpl implements CititorService {

    private final CititorRepository cititorRepository;

    public CititorServiceImpl(CititorRepository cititorRepository) {
        this.cititorRepository = cititorRepository;
    }

    public Cititor saveCititor(Cititor cititor) {
        Optional<Cititor> existingCititor = cititorRepository.findByCNP(cititor.getCNP());
        if(existingCititor.isPresent())
            throw new DuplicateCititorException();
        return cititorRepository.save(cititor);
    }

    public List<Cititor> getCititorAscNume() {
        return cititorRepository.findAllByOrderByNumeAsc();
    }

    public List<Cititor> getAllCititor(){
        return cititorRepository.findAll();
    }

    public void deleteById(Long id) {

        Optional<Cititor> cititor = cititorRepository.findById(id);
        if(cititor.isPresent()){
            cititorRepository.deleteById(id);
        }else
        {
            throw new CititorNotFoundException(id);
        }

    }
}
