package com.example.biblioteca.mapper;

import com.example.biblioteca.dto.ImprumutRequest;
import com.example.biblioteca.model.Imprumut;
import org.springframework.stereotype.Component;

@Component
public class ImprumutMapper {

    public Imprumut imprumutRequestToImprumut(ImprumutRequest imprumutRequest){
        return new Imprumut(imprumutRequest.getData_imprumut(),imprumutRequest.getData_returnare(),imprumutRequest.getCarte(),imprumutRequest.getCititor());
    }

}
