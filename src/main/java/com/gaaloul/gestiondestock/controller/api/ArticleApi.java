package com.gaaloul.gestiondestock.controller.api;

import com.gaaloul.gestiondestock.dto.ArticleDto;
import com.gaaloul.gestiondestock.dto.LigneCommandeClientDto;
import com.gaaloul.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.gaaloul.gestiondestock.dto.LigneVenteDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static  com.gaaloul.gestiondestock.utils.Constants.APP_ROOT;
import java.awt.*;
import java.util.List;

@Api(APP_ROOT+"/articles")
public interface ArticleApi {
    @PostMapping(value = APP_ROOT + "/articles/create" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE )
    @ApiOperation(value = "Enregistrer une article (ajouter/modifier)" ,notes = "cette methode permet d'enregistrer ou modifier un article",response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "l'objet article creer / modifier"),
            @ApiResponse( code = 400 , message = "l'objet article n'est pas valide")
    })
    ArticleDto save(@RequestBody ArticleDto dto);

    @GetMapping(value = APP_ROOT+ "/articles/{idArticle}" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher une article par ID" ,notes = "cette methode permet de rechercher un article par son ID",response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "l'article a ete trouver dans la BD"),
            @ApiResponse(code = 404 , message = "aucun article n'existe dans la BD avec ID fourni")
    })
    ArticleDto findById(@PathVariable("idArticle") Integer id);

    @GetMapping(value = APP_ROOT+ "/articles/{codeArticle}" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher une article par codeArticle" ,notes = "cette methode permet de rechercher un article par son codeArticle",response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "l'article a ete trouver dans la BD"),
            @ApiResponse(code = 404 , message = "aucun article n'existe dans la BD avec codeArticle fourni")
    })
    ArticleDto findByCodeArticle(@PathVariable("codeArticle") String codeArticle);

    @GetMapping(value = APP_ROOT+ "/articles/all" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "renvoie la listes des articles dans BD" ,notes = "cette methode permet de rechercher et renvoyer la liste des articles dans la BD",responseContainer ="List<ArticleDto>")
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "la liste des articles / une liste vide"),
    })
    List<ArticleDto> findAll();


    @GetMapping(value = APP_ROOT+ "/articles/historique/vente/{idArticle}" , produces = MediaType.APPLICATION_JSON_VALUE)
    List<LigneVenteDto> findHistoriqueVentes(@PathVariable("idArticle") Integer idArticle);

    @GetMapping(value = APP_ROOT+ "/articles/historique/commandeclient/{idArticle}" , produces = MediaType.APPLICATION_JSON_VALUE)
    List<LigneCommandeClientDto> findHistoriqueCommandeClient(@PathVariable("idArticle") Integer idArticle);

    @GetMapping(value = APP_ROOT+ "/articles/historique/commandefournisseur/{idArticle}" , produces = MediaType.APPLICATION_JSON_VALUE)
    List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(@PathVariable("idArticle") Integer idArticle);

    @GetMapping(value = APP_ROOT+ "/articles/filter/category/{idCategory}" , produces = MediaType.APPLICATION_JSON_VALUE)
    List<ArticleDto> findAllArticleByIdCategory(@PathVariable("idCategory")Integer idCategory);

    @DeleteMapping(value = APP_ROOT+"/articles/delete/{idArticle}")
    @ApiOperation(value = "supprimer un article par idArticle" ,notes = "cette methode permet de supprimer un article par son idArticle",response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "l'article a ete supprimer de la BD"),
    })
    void delete(@PathVariable("idArticle") Integer id);
}
