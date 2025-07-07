package com.example.biblioteca.controllers;




import com.example.biblioteca.dto.CarteRequest;
import com.example.biblioteca.dto.RecenzieRequest;
import com.example.biblioteca.mapper.RecenzieMapper;
import com.example.biblioteca.model.Carte;
import com.example.biblioteca.model.Recenzie;
import com.example.biblioteca.services.RecenzieService;
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
@RequestMapping("/recenzie")
@Tag(name = "Recenzii",description = "Endpoint administrare Recenzii")
public class RecenzieController {



    RecenzieService recenzieService;
    RecenzieMapper recenzieMapper;

    public RecenzieController(RecenzieService recenzieService, RecenzieMapper recenzieMapper) {
        this.recenzieService = recenzieService;
        this.recenzieMapper = recenzieMapper;
    }

    @GetMapping(path = "/asc-by-carte",produces = {MediaType.APPLICATION_JSON_VALUE })
    @Operation(description = "Afisarea recenziilor si a informatiilor despre acestea, ordonate alfabetic dupa titlul cartii",
            summary = "Afisare Recenzii alfabetic dupa carte",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
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
    public ResponseEntity<List<Recenzie>> GetRecenzieAscCarte(){
        return ResponseEntity.ok(recenzieService.getRecenzieAscCarte());
    }

    @GetMapping(path = "/asc-by-cititor",produces = {MediaType.APPLICATION_JSON_VALUE })
    @Operation(description = "Afisarea recenziilor si a informatiilor despre acestea, ordonate alfabetic dupa cititor",
            summary = "Afisare Recenzii alfabetic dupa cititor",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
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
    public ResponseEntity<List<Recenzie>> GetRecenzieAscCititor(){
        return ResponseEntity.ok(recenzieService.getRecenzieAscCititor());
    }


    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {MediaType.APPLICATION_JSON_VALUE })
    @Operation(description = "Creare recenzie - detalii necesare: data_recenzie, rating, review [nu e obligatoriu], id cititor, id carte",
            summary = "Crearea unei noi recenzii   ",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "201",
                            content = @Content(examples = {
                                    @ExampleObject(value = "{\"id\":1,\"data_recenzie\":\"2020-01-30\",\"rating\":5,\"review\":\"Test test\"" +
                                            ",\"cititor\":{\"id\":1},\"carte\":{\"id\":1}}"),
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
                                    @ExampleObject(value = "{\"data_recenzie\":\"30-01-2023\",\"rating\":7,\"review\":\"\"" +
                                            ",\"cititor\":{\"id\":1},\"carte\":{\"id\":1}}"),
                            }
                            )
                    ),
            })
    public ResponseEntity<Recenzie> createRecenzie(@Valid
                                             @RequestBody
                                             @Parameter(description = "MyDto")
                                             @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                     content = @Content(examples = {
                                                             @ExampleObject(value = "{\"data_recenzie\":\"2020-01-30\",\"rating\":5,\"review\":\"Test test\"" +
                                                                     ",\"cititor\":{\"id\":1},\"carte\":{\"id\":1}}"),
                                                     }
                                                     ))
                                                   RecenzieRequest recenzieRequest){
        Recenzie recenzie = recenzieService.saveRecenzie(recenzieMapper.recenzieRequestToRecenzie(recenzieRequest));
        return ResponseEntity.created(URI.create("/recenzie/" + recenzie.getId()))
                .body(recenzie);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(description = "Stergerea recenziei cu id dat",
            summary = "Stergerea unei recenzii cu id dat",
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
    public void deleteRecenzie(@PathVariable
                               @Parameter(name = "id",description = "Codul recenziei pe care doriti sa il stergeti",example = "1",required = true)
                               Long id)
    {
        recenzieService.deleteById(id);
    }



}
