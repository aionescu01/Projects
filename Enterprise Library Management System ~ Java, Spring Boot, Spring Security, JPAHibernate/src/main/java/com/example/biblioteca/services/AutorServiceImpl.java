package com.example.biblioteca.services;

import com.example.biblioteca.exception.AutorNotFoundException;
import com.example.biblioteca.exception.ImprumutNotFoundException;
import com.example.biblioteca.model.Autor;
import com.example.biblioteca.model.Imprumut;
import com.example.biblioteca.repositories.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorServiceImpl implements AutorService{

    private final AutorRepository autorRepository;

    public AutorServiceImpl(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    //@Override
    public Autor saveAutor(Autor autor) {
        return autorRepository.save(autor);
    }

    //@Override
    public List<Autor> getAutorAscNume() {
        return autorRepository.findAllByOrderByNumeAsc();
    }

    public List<Autor> getAllAutor() {
        return autorRepository.findAll();
    }

    public void deleteById(Long id) {

        Optional<Autor> autor = autorRepository.findById(id);
        if(autor.isPresent()){
            autorRepository.deleteById(id);
        }else
        {
            throw new AutorNotFoundException(id);
        }

    }
}
