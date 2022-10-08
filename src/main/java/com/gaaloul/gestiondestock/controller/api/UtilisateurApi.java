package com.gaaloul.gestiondestock.controller.api;

import com.gaaloul.gestiondestock.dto.MvtStkDto;
import com.gaaloul.gestiondestock.dto.UtilisateurDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import static com.gaaloul.gestiondestock.utils.Constants.APP_ROOT;
import static com.gaaloul.gestiondestock.utils.Constants.UTILISATEUR_ENDPOINT;

@Api(UTILISATEUR_ENDPOINT)
public interface UtilisateurApi {

    @PostMapping(value = UTILISATEUR_ENDPOINT + "/create" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE )
    @ApiOperation(value = "Enregistrer un utilisateur (ajouter/modifier)" ,notes = "cette methode permet d'enregistrer ou modifier un utilisateur",response = MvtStkDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "l'objet MvtStk creer / modifier"),
            @ApiResponse( code = 400 , message = "l'objet MvtStk n'est pas valide")
    })
    UtilisateurDto save(UtilisateurDto dto);


    @GetMapping(value = UTILISATEUR_ENDPOINT+ "/{idutilisateur}" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher un utilisateur par ID" ,notes = "cette methode permet de rechercher un utilisateur par son ID",response = UtilisateurDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "le utilisateur a ete trouver dans la BD"),
            @ApiResponse(code = 404 , message = "aucun utilisateur n'existe dans la BD avec ID fourni")
    })
    UtilisateurDto findById(@PathVariable("idutilisateur")Integer id);


    @GetMapping(value = UTILISATEUR_ENDPOINT+ "/all" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "renvoie la listes des utilisateurs dans BD" ,notes = "cette methode permet de rechercher et renvoyer la liste des utilisateurs dans la BD",responseContainer ="List<tilisateurDto>")
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "la liste des tilisateur / une liste vide"),
    })
    List<UtilisateurDto> findAll();


    @DeleteMapping(value = UTILISATEUR_ENDPOINT+"/delete/{idutilisateur}")
    @ApiOperation(value = "supprimer un utilisateur par idutilisateur" ,notes = "cette methode permet de supprimer un utilisateur par son idMvtStk",response = UtilisateurDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "le utilisateur a ete supprimer de la BD"),
    })
    void delete(Integer id);
}
