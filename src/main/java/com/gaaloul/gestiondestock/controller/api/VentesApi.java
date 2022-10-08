package com.gaaloul.gestiondestock.controller.api;

import com.gaaloul.gestiondestock.dto.ArticleDto;
import com.gaaloul.gestiondestock.dto.VentesDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gaaloul.gestiondestock.utils.Constants.APP_ROOT;
import static com.gaaloul.gestiondestock.utils.Constants.VENTE_ENDPOINT;

@Api(VENTE_ENDPOINT)
public interface VentesApi {
    @PostMapping(value = VENTE_ENDPOINT + "/create" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE )
    @ApiOperation(value = "Enregistrer une vente (ajouter/modifier)" ,notes = "cette methode permet d'enregistrer ou modifier un vente",response = VentesDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "l'objet vente creer / modifier"),
            @ApiResponse( code = 400 , message = "l'objet vente n'est pas valide")
    })
    VentesDto save(@RequestBody  VentesDto dto);


    @GetMapping(value = VENTE_ENDPOINT+ "/{idVentes}" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher une vente par ID" ,notes = "cette methode permet de rechercher un vente par son ID",response = VentesDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "la vente a ete trouver dans la BD"),
            @ApiResponse(code = 404 , message = "aucune vente n'existe dans la BD avec ID fourni")
    })
    VentesDto findById(@PathVariable("idVentes")Integer id);


    @GetMapping(value = VENTE_ENDPOINT+ "/{codeVentes}" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher une Vente par codeArticle" ,notes = "cette methode permet de rechercher un Vente par son codeVentes",response = VentesDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "la Vente a ete trouver dans la BD"),
            @ApiResponse(code = 404 , message = "aucun Vente n'existe dans la BD avec codeVente fourni")
    })
    VentesDto findByCode(@PathVariable("codeVentes") String code);


    @GetMapping(value = VENTE_ENDPOINT+ "/all" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "renvoie la listes des ventes dans BD" ,notes = "cette methode permet de rechercher et renvoyer la liste des ventes dans la BD",responseContainer ="List<VentesDto>")
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "la liste des ventes / une liste vide"),
    })
    List<VentesDto> findAll();


    @DeleteMapping(value = VENTE_ENDPOINT+"/delete/{idVentes}")
    @ApiOperation(value = "supprimer un vente par idVentes" ,notes = "cette methode permet de supprimer un vente par son idVentes",response = VentesDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "la vente a ete supprimer de la BD"),
    })
    void delete(Integer id);

}
