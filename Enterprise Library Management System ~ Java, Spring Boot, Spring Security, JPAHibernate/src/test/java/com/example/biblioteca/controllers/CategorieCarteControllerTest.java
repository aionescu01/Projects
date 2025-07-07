package com.example.biblioteca.controllers;

import com.example.biblioteca.dto.CarteRequest;
import com.example.biblioteca.dto.CategorieCarteRequest;
import com.example.biblioteca.mapper.CarteMapper;
import com.example.biblioteca.mapper.CategorieCarteMapper;
import com.example.biblioteca.model.Autor;
import com.example.biblioteca.model.Carte;
import com.example.biblioteca.model.CategorieCarte;
import com.example.biblioteca.services.CarteService;
import com.example.biblioteca.services.CategorieCarteService;
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

@WebMvcTest(controllers = CategorieCarteController.class)
public class CategorieCarteControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CategorieCarteService categorieCarteService;
    @MockBean
    private CategorieCarteMapper categorieCarteMapper;

    @Test
    public void createCategorieCarte() throws Exception{

        CategorieCarteRequest request = new CategorieCarteRequest("test");
        when(categorieCarteService.saveCategorieCarte(any())).thenReturn(new CategorieCarte(1,"test"));
        mockMvc.perform(post("/categorie_carte")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nume").value(request.getNume()));
    }

    @Test
    public void getAllCategorieCarte() throws Exception {

        CategorieCarte cat = new CategorieCarte(1,"test");
        CategorieCarte cat2 = new CategorieCarte(2,"test2");



        List<CategorieCarte> categorieCarteList = new ArrayList<>();
        categorieCarteList.add(cat);
        categorieCarteList.add(cat2);

        when(categorieCarteService.getCategorieCarteAscNume()).thenReturn(categorieCarteList);
        mockMvc.perform(get("/categorie_carte/asc")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(categorieCarteList.size()))
                .andExpect(jsonPath("$[0].nume").value(categorieCarteList.get(0).getNume()))
                .andExpect(jsonPath("$[1].nume").value(categorieCarteList.get(1).getNume()));
    }

    @Test
    public void deleteCategorieCarte() throws Exception{
        Long id = 1L;
        categorieCarteService.deleteById(id);
        mockMvc.perform(delete("/categorie_carte/{id}",id)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }
}
