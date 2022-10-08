package com.gaaloul.gestiondestock.controller.api;

import com.gaaloul.gestiondestock.dto.ArticleDto;
import com.gaaloul.gestiondestock.dto.CategoryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gaaloul.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT+"/categories")
public interface CategoryApi {
    @PostMapping(value = APP_ROOT + "/categories/create" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE )
    @ApiOperation(value = "Enregistrer une categorie (ajouter/modifier)" ,notes = "cette methode permet d'enregistrer ou modifier une categorie",response = CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "l'objet categorie creer / modifier"),
            @ApiResponse( code = 400 , message = "l'objet categorie n'est pas valide")
    })
    CategoryDto save(@RequestBody CategoryDto dto);


    @GetMapping(value = APP_ROOT+ "/categories/{idCategory}" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher une categorie par ID" ,notes = "cette methode permet de rechercher un categorie par son ID",response = CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "la categorie a ete trouver dans la BD"),
            @ApiResponse(code = 404 , message = "aucune categorie n'existe dans la BD avec ID fourni")
    })
    CategoryDto findById(@PathVariable("idCategory") Integer idCategory);


    @GetMapping(value = APP_ROOT+ "/categories/{codeCategory}" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher une categorie par code categorie" ,notes = "cette methode permet de rechercher une categorie par son code categorie",response = CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "la categorie a ete trouver dans la BD"),
            @ApiResponse(code = 404 , message = "aucune categorie n'existe dans la BD avec code categorie fourni")
    })
    CategoryDto findByCodeCategory(@PathVariable("codeCategory") String codeCategory);


    @GetMapping(value = APP_ROOT+ "/categories/all" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "renvoie la listes des categories dans BD" ,notes = "cette methode permet de rechercher et renvoyer la liste des categories dans la BD",responseContainer ="List<CategoryDto>")
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "la liste des categories / une liste vide"),
    })
    List<CategoryDto> findAll();



    @DeleteMapping(value = APP_ROOT+"/categories/delete/{idCategory}")
    @ApiOperation(value = "supprimer une categorie par idCategory" ,notes = "cette methode permet de supprimer une categorie par son idCategory",response = CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "la categorie a ete supprimer de la BD"),
    })
    void delete(@PathVariable("idCategory")Integer id);
}

