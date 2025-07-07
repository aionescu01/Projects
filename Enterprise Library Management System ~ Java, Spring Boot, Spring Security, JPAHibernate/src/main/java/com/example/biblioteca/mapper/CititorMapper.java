package com.example.biblioteca.mapper;

import com.example.biblioteca.dto.CititorRequest;
import com.example.biblioteca.model.Cititor;
import org.springframework.stereotype.Component;

@Component
public class CititorMapper {

    public Cititor  cititorRequestToCititor(CititorRequest cititorRequest){
        return new Cititor(cititorRequest.getNume(),cititorRequest.getPrenume(), cititorRequest.getEmail(), cititorRequest.getNr_telefon(), cititorRequest.getCNP(), cititorRequest.getData_nasterii());
    }

}
