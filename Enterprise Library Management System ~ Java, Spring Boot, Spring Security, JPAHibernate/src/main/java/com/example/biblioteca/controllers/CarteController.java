package com.example.biblioteca.controllers;

import com.example.biblioteca.dto.CarteRequest;
import com.example.biblioteca.mapper.CarteMapper;
import com.example.biblioteca.model.Carte;
import com.example.biblioteca.services.CarteService;
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
@RequestMapping("/carte")
@Tag(name = "Carti",description = "Endpoint administrare Carti")
public class CarteController {

    CarteService carteService;
    CarteMapper carteMapper;

    public CarteController(CarteService carteService, CarteMapper carteMapper) {
        this.carteService = carteService;
        this.carteMapper = carteMapper;
    }


    @GetMapping(path = "/asc",produces = {MediaType.APPLICATION_JSON_VALUE })
    @Operation(description = "Afisarea cartilor si a informatiilor legate de fiecare, fiind ordonate alfabetic",
            summary = "Afisare Carti alfabetic",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(examples = {
                                    @ExampleObject( value = "{\"id\":\"1\",\"titlu\":\"Amintiri din copilarie\",\"data_publicare\":\"2020-01-30\",\"autor\":{\"id\":1,\"nume\":\"Creanga\",\"prenume\":\"Ion\"},\"categorie\":{\"id\":1,\"nume\":\"Memorii\"}}"),
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
    public ResponseEntity<List<Carte>> GetCarteAscNume(){
        return ResponseEntity.ok(carteService.getCarteAscNume());
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {MediaType.APPLICATION_JSON_VALUE })
    @Operation(description = "Creare carte - detalii necesare: titlul, data publicarii, id autor, id categorie",
            summary = "Crearea unei noi carti",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "201",
                            content = @Content(examples = {
                                    @ExampleObject( value = "{\"id\":\"1\",\"titlu\":\"Amintiri din copilarie\",\"data_publicare\":\"2020-01-30\",\"autor\":{\"id\":1,\"nume\":\"Creanga\",\"prenume\":\"Ion\"},\"categorie\":{\"id\":1,\"nume\":\"Memorii\"}}"),
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
                                    @ExampleObject(value = "{\"data_publicare\":\"2020-01-30\",\"titlu\":\"\",\"autor\":{\"id\":1},\"categorie\":{\"id\":1}}"),
                            }
                            )
                    ),
            })
    public ResponseEntity<Carte> createCarte(@Valid
                                                   @RequestBody
                                                   @Parameter(description = "MyDto")
                                                   @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                           content = @Content(examples = {
                                                                   @ExampleObject(value = "{\"data_publicare\":\"2020-01-30\",\"titlu\":\"Test\",\"autor\":{\"id\":1},\"categorie\":{\"id\":1}}"),
                                                                   }
                                                           ))
                                                   CarteRequest carteRequest){
        Carte carte = carteService.saveCarte(carteMapper.carteRequestToCarte(carteRequest));
        return ResponseEntity.created(URI.create("/carte/" + carte.getId()))
                .body(carte);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(description = "Stergerea cartii cu id dat",
            summary = "Stergerea unei carti cu id dat",
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
    public void deleteCarte(@PathVariable
                               @Parameter(name = "id",description = "Codul cartii pe care doriti sa o stergeti",example = "1",required = true)
                               Long id)
    {
        carteService.deleteById(id);
    }
}
