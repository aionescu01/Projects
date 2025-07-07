package com.example.biblioteca.controllers;

import com.example.biblioteca.dto.CarteRequest;
import com.example.biblioteca.dto.CititorRequest;
import com.example.biblioteca.mapper.CarteMapper;
import com.example.biblioteca.mapper.CititorMapper;
import com.example.biblioteca.model.Autor;
import com.example.biblioteca.model.Carte;
import com.example.biblioteca.model.CategorieCarte;
import com.example.biblioteca.model.Cititor;
import com.example.biblioteca.services.CarteService;
import com.example.biblioteca.services.CititorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CititorController.class)
public class CititorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CititorService cititorService;
    @MockBean
    private CititorMapper cititorMapper;


    @Test
    public void createCititor() throws Exception{
        CititorRequest request = new CititorRequest("test","test","test","test","1231231231231",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-22T12:00:00"));

        when(cititorService.saveCititor(any())).thenReturn(new Cititor("test","test","test","test","1231231231231",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-22T12:00:00")));
        mockMvc.perform(post("/cititor")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cnp").value(request.getCNP()));
    }

    @Test
    public void getAllCititor() throws Exception {


        Cititor cit1 = new Cititor("test","test","test","test","1231231231231",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-22T12:00:00"));
        Cititor cit2 = new Cititor("test2","test","test","test","1231231231232",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-22T12:00:00"));

        List<Cititor> cititorList = new ArrayList<>();
        cititorList.add(cit1);
        cititorList.add(cit2);

        when(cititorService.getCititorAscNume()).thenReturn(cititorList);
        mockMvc.perform(get("/cititor/asc")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(cititorList.size()))
                .andExpect(jsonPath("$[0].nume").value(cititorList.get(0).getNume()))
                .andExpect(jsonPath("$[1].nume").value(cititorList.get(1).getNume()));
    }


    @Test
    public void deleteCititor() throws Exception{
        Long id = 1L;
        cititorService.deleteById(id);
        mockMvc.perform(delete("/cititor/{id}",id)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

}
