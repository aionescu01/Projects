package com.example.biblioteca.controllers;


import com.example.biblioteca.dto.CarteRequest;
import com.example.biblioteca.dto.CititorRequest;
import com.example.biblioteca.mapper.CititorMapper;
import com.example.biblioteca.model.Carte;
import com.example.biblioteca.model.Cititor;
import com.example.biblioteca.services.CititorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController()
@Validated
@RequestMapping("/cititor")
@Tag(name = "Cititori",description = "Endpoint administrare Cititori")
public class CititorController {

    CititorService cititorService;
    CititorMapper cititorMapper;

    public CititorController(CititorService cititorService, CititorMapper cititorMapper) {
        this.cititorService = cititorService;
        this.cititorMapper = cititorMapper;
    }


    @GetMapping(path = "/asc",produces = {MediaType.APPLICATION_JSON_VALUE })
    @Operation(description = "Afisarea cititorilor si a informatiilor despre acestea, ordonate alfabetic",
            summary = "Afisare Cititori alfabetic",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(examples = {
                                    @ExampleObject(value = "{\"id\": 1, \"nume\": \"test\", \"prenume\": \"test\"," +
                                            "\"email\": \"test\", \"nr_telefon\": \"0000000\", \"cnp\": \"0000000000000\", \"data_nasterii\": \"2022-01-12\"}"),
                            }
                            )
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404",
                            content = @Content(examples = {
                                    @ExampleObject(),
                            }
                            )
                    ),
            })
    public ResponseEntity<List<Cititor>> GetCititorAscNume(){
        return ResponseEntity.ok(cititorService.getCititorAscNume());
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {MediaType.APPLICATION_JSON_VALUE })
    @Operation(description = "Creare cititor - detalii necesare: nume, prenume, email, nr_telefon [obligatoriu], cnp [obligatoriu], data_nasterii",
            summary = "Crearea unui nou cititor   ",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "201",
                            content = @Content(examples = {
                                    @ExampleObject(value = "{\"id\": 1, \"nume\": \"test\", \"prenume\": \"test\"," +
                                            "\"email\": \"test\", \"nr_telefon\": \"0000000\", \"cnp\": \"0000000000000\", \"data_nasterii\": \"2022-01-12\"}"),
                            }
                            )
                    ),
                    @ApiResponse(
                            description = "Bad Request - validation error per request",
                            responseCode = "500",
                            content = @Content(examples = {
                                    @ExampleObject(),
                            }
                            )
                    ),
                    @ApiResponse(
                            description = "Field validation error",
                            responseCode = "400",
                            content = @Content(examples = {
                                    @ExampleObject(value = "{\"nume\": \"test_\", \"prenu3me\": \"test\"," +
                                            "\"email\": \"test\", \"nr_telefon\": \"00a00000\", \"cnp\": \"aa0000000a000000\", \"data_nasterii\": \"12/01/2022\"}"),
                            }
                            )
                    ),
            })
    public ResponseEntity<Cititor> createCititor(@Valid
                                             @RequestBody
                                             @Parameter(description = "MyDto")
                                             @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                     content = @Content(examples = {
                                                             @ExampleObject(value = "{\"nume\": \"test\", \"prenume\": \"test\"," +
                                                                     "\"email\": \"test\", \"nr_telefon\": \"0000000\", \"cnp\": \"0000000000000\", \"data_nasterii\": \"2022-01-12\"}"),
                                                     }
                                                     ))
                                                 CititorRequest cititorRequest){
        Cititor cititor = cititorService.saveCititor(cititorMapper.cititorRequestToCititor(cititorRequest));
        return ResponseEntity.created(URI.create("/cititor/" + cititor.getId()))
                .body(cititor);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(description = "Stergerea cititorului cu id dat",
            summary = "Stergerea unui cititor cu id dat",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404"
                    ),
            })
    public void deleteCititor(@PathVariable
                               @Parameter(name = "id",description = "Codul cititorului pe care doriti sa il stergeti",example = "1",required = true)
                               Long id)
    {
        cititorService.deleteById(id);
    }

}
