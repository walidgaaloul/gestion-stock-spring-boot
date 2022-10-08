package com.gaaloul.gestiondestock.controller.api;

import com.gaaloul.gestiondestock.dto.ClientDto;
import com.gaaloul.gestiondestock.dto.FournisseurDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gaaloul.gestiondestock.utils.Constants.APP_ROOT;
import static com.gaaloul.gestiondestock.utils.Constants.FOURNISSEUR_ENDPOINT;

@Api(FOURNISSEUR_ENDPOINT)
public interface FournisseurApi {

    @PostMapping(value = FOURNISSEUR_ENDPOINT + "/create" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE )
    @ApiOperation(value = "Enregistrer un client (ajouter/modifier)" ,notes = "cette methode permet d'enregistrer ou modifier un fournisseur",response = FournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "l'objet fournisseur creer / modifier"),
            @ApiResponse( code = 400 , message = "l'objet fournisseur n'est pas valide")
    })
    FournisseurDto save(@RequestBody FournisseurDto dto);


    @GetMapping(value = FOURNISSEUR_ENDPOINT+ "/{idFournisseur}" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher un fournisseur par ID" ,notes = "cette methode permet de rechercher un fournisseur par son ID",response = FournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "le fournisseur a ete trouver dans la BD"),
            @ApiResponse(code = 404 , message = "aucun fournisseur n'existe dans la BD avec ID fourni")
    })
    FournisseurDto findById(@PathVariable("idFournisseur")Integer id);


    @GetMapping(value = FOURNISSEUR_ENDPOINT+ "/all" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "renvoie la listes des fournisseurs dans BD" ,notes = "cette methode permet de rechercher et renvoyer la liste des fournisseurs dans la BD",responseContainer ="List<FournisseurDto>")
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "la liste des fournisseurs / une liste vide"),
    })
    List<FournisseurDto> findAll();


    @DeleteMapping(value = FOURNISSEUR_ENDPOINT+"/delete/{idFournisseur}")
    @ApiOperation(value = "supprimer un fournisseurs par idFournisseur" ,notes = "cette methode permet de supprimer un Fournisseur par son idFournisseur",response = FournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "le fournisseur a ete supprimer de la BD"),
    })
    void delete(@PathVariable("idFournisseur")Integer id);
}
