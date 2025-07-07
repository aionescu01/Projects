package com.example.biblioteca.services;

import com.example.biblioteca.dto.ImprumutUpdateDto;
import com.example.biblioteca.exception.ImprumutNotFoundException;
import com.example.biblioteca.model.Imprumut;
import com.example.biblioteca.repositories.ImprumutRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImprumutServiceImpl implements ImprumutService{

    private final ImprumutRepository imprumutRepository;

    public ImprumutServiceImpl(ImprumutRepository imprumutRepository) {
        this.imprumutRepository = imprumutRepository;
    }


    public Imprumut saveImprumut(Imprumut imprumut) {
        return imprumutRepository.save(imprumut);
    }

    public List<Imprumut> getImprumutAscId() {
        return imprumutRepository.findAllByOrderByIdAsc();
    }

    public Imprumut updateImprumut(Long id, ImprumutUpdateDto imprumut) {
        Optional<Imprumut> imprumutOptional = imprumutRepository.findById(id);
        if(imprumutOptional.isPresent()){
            Imprumut imp = imprumutOptional.get();
            imp.setData_returnare(imprumut.getData_returnare());
            return imprumutRepository.save(imp);
        }else
        {
            throw new ImprumutNotFoundException(id);
        }
    }

    public void deleteById(Long id) {

        Optional<Imprumut> imprumut = imprumutRepository.findById(id);
        if(imprumut.isPresent()){
            imprumutRepository.deleteById(id);
        }else
        {
            throw new ImprumutNotFoundException(id);
        }

    }

    public List<Imprumut> getAllImprumut() {
        return imprumutRepository.findAll();
    }
}
