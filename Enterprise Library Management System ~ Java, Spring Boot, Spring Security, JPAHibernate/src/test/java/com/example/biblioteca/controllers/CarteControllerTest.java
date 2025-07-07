package com.example.biblioteca.controllers;

import com.example.biblioteca.dto.AutorRequest;
import com.example.biblioteca.dto.CarteRequest;
import com.example.biblioteca.mapper.CarteMapper;
import com.example.biblioteca.model.Autor;
import com.example.biblioteca.model.Carte;
import com.example.biblioteca.model.CategorieCarte;
import com.example.biblioteca.services.CarteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CarteController.class)
public class CarteControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CarteService carteService;
    @MockBean
    private CarteMapper carteMapper;


    @Test
    public void createCarte() throws Exception{
        Autor autor1 = new Autor(1,"testNume","testPrenume");
        CategorieCarte cat = new CategorieCarte(1,"test");
        CarteRequest request = new CarteRequest("test",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-22T12:00:00"),autor1,cat);
        when(carteService.saveCarte(any())).thenReturn(new Carte(1,"test",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-22T12:00:00"),autor1,cat));
        mockMvc.perform(post("/carte")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titlu").value(request.getTitlu()));
    }

    @Test
    public void getAllCarte() throws Exception {

        Autor autor1 = new Autor(1,"testNume","testPrenume");
        CategorieCarte cat = new CategorieCarte(1,"test");


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("EET"));

        Carte carte = new Carte("test",sdf.parse("2023-03-22T10:00:00"),autor1,cat);
        Carte carte2 = new Carte("test2",sdf.parse("2023-03-22T10:00:00"),autor1,cat);


        List<Carte> carteList = new ArrayList<>();
        carteList.add(carte);
        carteList.add(carte2);

        when(carteService.getCarteAscNume()).thenReturn(carteList);
        mockMvc.perform(get("/carte/asc")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(carteList.size()))
                .andExpect(jsonPath("$[0].titlu").value(carteList.get(0).getTitlu()))
                .andExpect(jsonPath("$[1].titlu").value(carteList.get(1).getTitlu()));
    }

    @Test
    public void deleteCarte() throws Exception{
        Long id = 1L;
        carteService.deleteById(id);
        mockMvc.perform(delete("/carte/{id}",id)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }


}
