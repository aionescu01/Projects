package com.example.biblioteca.dto;

import com.example.biblioteca.model.Carte;
import com.example.biblioteca.model.Cititor;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class ImprumutUpdateDto {

    @NotNull(message = "Data de imprumut nu poate fi null")
    private Date data_returnare;

    public ImprumutUpdateDto() {
    }


    public ImprumutUpdateDto(Date data_returnare) {
        this.data_returnare = data_returnare;
    }

    public Date getData_returnare() {
        return data_returnare;
    }

    public void setData_returnare(Date data_returnare) {
        this.data_returnare = data_returnare;
    }
}
