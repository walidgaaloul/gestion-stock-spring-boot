package com.gaaloul.gestiondestock.controller.api;

import com.gaaloul.gestiondestock.dto.ClientDto;
import com.gaaloul.gestiondestock.dto.EntrepriseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gaaloul.gestiondestock.utils.Constants.APP_ROOT;
import static com.gaaloul.gestiondestock.utils.Constants.ENTREPRISE_ENDPOINT;

@Api(ENTREPRISE_ENDPOINT )
public interface EntrepriseApi {

    @PostMapping(value = ENTREPRISE_ENDPOINT + "/create" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE )
    @ApiOperation(value = "Enregistrer une entreprise (ajouter/modifier)" ,notes = "cette methode permet d'enregistrer ou modifier une entreprise",response = EntrepriseDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "l'objet entreprise creer / modifier"),
            @ApiResponse( code = 400 , message = "l'objet entreprise n'est pas valide")
    })
    EntrepriseDto save(@RequestBody EntrepriseDto dto);


    @GetMapping(value = ENTREPRISE_ENDPOINT+ "/{idEntreprise}" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher une entreprise par ID" ,notes = "cette methode permet de rechercher une entreprise par son ID",response = EntrepriseDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "l'entreprise a ete trouver dans la BD"),
            @ApiResponse(code = 404 , message = "aucune entreprise n'existe dans la BD avec ID fourni")
    })
    EntrepriseDto findById(@PathVariable("idEntreprise")Integer id);


    @GetMapping(value = ENTREPRISE_ENDPOINT+ "/all" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "renvoie la listes des entreprises dans BD" ,notes = "cette methode permet de rechercher et renvoyer la liste des entreprises dans la BD",responseContainer ="List<EntrepriseDto>")
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "la liste des entreprises / une liste vide"),
    })
    List<EntrepriseDto> findAll();


    @DeleteMapping(value = ENTREPRISE_ENDPOINT+"/delete/{idEntreprise}")
    @ApiOperation(value = "supprimer une entreprise par idEntreprise" ,notes = "cette methode permet de supprimer une entreprise par son idEntreprise",response = EntrepriseDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "l'entreprise a ete supprimer de la BD"),
    })
    void delete(@PathVariable("idEntreprise")Integer id);
}
