package com.gaaloul.gestiondestock.controller.api;

import com.gaaloul.gestiondestock.dto.CategoryDto;
import com.gaaloul.gestiondestock.dto.ClientDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gaaloul.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT+"/clients")
public interface ClientApi {

    @PostMapping(value = APP_ROOT + "/clients/create" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE )
    @ApiOperation(value = "Enregistrer un client (ajouter/modifier)" ,notes = "cette methode permet d'enregistrer ou modifier un client",response = ClientDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "l'objet client creer / modifier"),
            @ApiResponse( code = 400 , message = "l'objet client n'est pas valide")
    })
    ClientDto save(@RequestBody ClientDto dto);


    @GetMapping(value = APP_ROOT+ "/clients/{idClient}" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher un client par ID" ,notes = "cette methode permet de rechercher un client par son ID",response = ClientDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "le client a ete trouver dans la BD"),
            @ApiResponse(code = 404 , message = "aucun client n'existe dans la BD avec ID fourni")
    })
    ClientDto findById(@PathVariable("idClient")Integer id);


    @GetMapping(value = APP_ROOT+ "/clients/all" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "renvoie la listes des clients dans BD" ,notes = "cette methode permet de rechercher et renvoyer la liste des clients dans la BD",responseContainer ="List<ClientDto>")
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "la liste des clients / une liste vide"),
    })
    List<ClientDto> findAll();


    @DeleteMapping(value = APP_ROOT+"/clients/delete/{idClient}")
    @ApiOperation(value = "supprimer une client par idClient" ,notes = "cette methode permet de supprimer un client par son idClient",response = ClientDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "l client a ete supprimer de la BD"),
    })
    void delete(@PathVariable("idClient")Integer id);
}
