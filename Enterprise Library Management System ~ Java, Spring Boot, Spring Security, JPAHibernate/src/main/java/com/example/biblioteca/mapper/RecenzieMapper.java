package com.example.biblioteca.mapper;

import com.example.biblioteca.dto.RecenzieRequest;
import com.example.biblioteca.model.Recenzie;
import org.springframework.stereotype.Component;

@Component
public class RecenzieMapper {

    public Recenzie recenzieRequestToRecenzie(RecenzieRequest recenzieRequest){
        return new Recenzie(recenzieRequest.getRating(), recenzieRequest.getReview(), recenzieRequest.getData_recenzie(),recenzieRequest.getCititor(),recenzieRequest.getCarte());
    }

}
