package com.example.biblioteca.controllers;

import com.example.biblioteca.dto.AutorRequest;
import com.example.biblioteca.dto.ImprumutRequest;
import com.example.biblioteca.dto.ImprumutUpdateDto;
import com.example.biblioteca.mapper.ImprumutMapper;
import com.example.biblioteca.model.*;
import com.example.biblioteca.services.ImprumutService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = ImprumutController.class)

public class ImprumutControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ImprumutService imprumutService;
    @MockBean
    private ImprumutMapper imprumutMapper;

    @Test
    public void createImprumut() throws Exception{
        ImprumutRequest request = new ImprumutRequest();
        request.setData_imprumut(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-22T12:00:00"));
        request.setData_returnare(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-27T12:00:00"));
        Autor a = new Autor(1,"test","test");
        CategorieCarte cat = new CategorieCarte(1,"test");
        Cititor cit = new Cititor(1,"test","test","test","test","1111111111111",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2000-03-27T12:00:00"));
        Carte c = new Carte(1,"test",a,cat);
        request.setCarte(c);
        request.setCititor(cit);


        Imprumut imprumutReturn = new Imprumut();
        imprumutReturn.setId(1);
        imprumutReturn.setData_imprumut(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-22T12:00:00"));
        imprumutReturn.setData_returnare(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-27T12:00:00"));
        imprumutReturn.setCarte(c);
        imprumutReturn.setCititor(cit);


        when(imprumutService.saveImprumut(any())).thenReturn(imprumutReturn);
        mockMvc.perform(post("/imprumut")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data_imprumut").value("2023-03-22T10:00:00.000+00:00"))
                .andExpect(jsonPath("$.data_returnare").value("2023-03-27T09:00:00.000+00:00"))
                .andExpect(jsonPath("$.carte").value(request.getCarte()));
    }

    @Test
    public void getAllImprumuturi()throws Exception{

        Autor a = new Autor(1,"test","test");
        CategorieCarte cat = new CategorieCarte(1,"test");
        Cititor cit = new Cititor(1,"test","test","test","test","1111111111111",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2000-03-27T12:00:00"));
        Carte c = new Carte(1,"test",a,cat);

        Imprumut imprumut1 = new Imprumut();
        imprumut1.setId(1);
        imprumut1.setData_imprumut(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-22T12:00:00"));
        imprumut1.setData_returnare(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-27T12:00:00"));
        imprumut1.setCarte(c);
        imprumut1.setCititor(cit);

        Imprumut imprumut2 = new Imprumut();
        imprumut2.setId(2);
        imprumut2.setData_imprumut(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-22T12:00:00"));
        imprumut2.setData_returnare(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-27T12:00:00"));
        imprumut2.setCarte(c);
        imprumut2.setCititor(cit);

        List<Imprumut> imprumuturiList = new ArrayList<>();
        imprumuturiList.add(imprumut1);
        imprumuturiList.add(imprumut2);

        when(imprumutService.getImprumutAscId()).thenReturn(imprumuturiList);
        mockMvc.perform(get("/imprumut/asc")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(imprumuturiList.size()))
                .andExpect(jsonPath("$[0].carte").value(imprumuturiList.get(0).getCarte()))
                .andExpect(jsonPath("$[0].data_imprumut").value("2023-03-22T10:00:00.000+00:00"))
                .andExpect(jsonPath("$[0].data_returnare").value("2023-03-27T09:00:00.000+00:00"))
                .andExpect(jsonPath("$[1].carte").value(imprumuturiList.get(1).getCarte()))
                .andExpect(jsonPath("$[1].data_imprumut").value("2023-03-22T10:00:00.000+00:00"))
                .andExpect(jsonPath("$[1].data_returnare").value("2023-03-27T09:00:00.000+00:00"));
    }



    @Test
    public void updateImprumut() throws Exception{
        Autor a = new Autor(1,"test","test");
        CategorieCarte cat = new CategorieCarte(1,"test");
        Cititor cit = new Cititor(1,"test","test","test","test","1111111111111",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2000-03-27T12:00:00"));
        Carte c = new Carte(1,"test",a,cat);

        Imprumut imprumut1 = new Imprumut();
        imprumut1.setId(1);
        imprumut1.setData_imprumut(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-22T12:00:00"));
        imprumut1.setData_returnare(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-27T12:00:00"));
        imprumut1.setCarte(c);
        imprumut1.setCititor(cit);

        ImprumutUpdateDto imprumut2 = new ImprumutUpdateDto(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-28T12:00:00"));

        Imprumut imprumut3 = new Imprumut();
        imprumut3.setId(1);
        imprumut3.setData_imprumut(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-22T12:00:00"));
        imprumut3.setData_returnare(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-28T12:00:00"));
        imprumut3.setCarte(c);
        imprumut3.setCititor(cit);

        when(imprumutService.updateImprumut(any(),any())).thenReturn(imprumut3);

        mockMvc.perform(put("/imprumut/{id}",1)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(imprumut2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data_returnare").value("2023-03-28T09:00:00.000+00:00"));

    }





    @Test
    public void deleteImprumut() throws Exception{
        Long id = 1L;
        imprumutService.deleteById(id);
        mockMvc.perform(delete("/imprumut/{id}",id)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }


}
