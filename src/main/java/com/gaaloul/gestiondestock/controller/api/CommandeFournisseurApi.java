package com.gaaloul.gestiondestock.controller.api;

import com.gaaloul.gestiondestock.dto.CommandeFournisseurDto;
import com.gaaloul.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.gaaloul.gestiondestock.model.EtatCommande;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.gaaloul.gestiondestock.utils.Constants.*;

@Api(COMMANDE_FOURNISSEUR_ENDPOINT)
public interface CommandeFournisseurApi {


    @PatchMapping(value = COMMANDE_FOURNISSEUR_ENDPOINT  + "/update/etat/{idCommande}/{etatCommande}" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE )
    CommandeFournisseurDto  updateEtatCommande(@PathVariable("idCommande") Integer idCommande , @PathVariable("etatCommande") EtatCommande etatCommande);


    @PatchMapping(value = COMMANDE_FOURNISSEUR_ENDPOINT + "/update/quantite/{idCommande}/{idLigneCommande}/{quantite}" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE )
    CommandeFournisseurDto  updateQuantiteCommande(@PathVariable("idCommande")Integer idCommande,@PathVariable("idLigneCommande") Integer idLigneCommande,@PathVariable("quantite") BigDecimal quantite);


    @PatchMapping(value = COMMANDE_FOURNISSEUR_ENDPOINT + "/update/fournisseur/{idCommande}/{idFournisseur}" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE )
    CommandeFournisseurDto updateFournisseur(@PathVariable("idCommande")Integer idCommande,@PathVariable("idFournisseur") Integer idFournisseur);


    @PatchMapping(value = COMMANDE_FOURNISSEUR_ENDPOINT + "/update/article/{idCommande}/{idLigneCommande}/{idArticle}" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE )
    CommandeFournisseurDto updateArticle(@PathVariable("idCommande")Integer idCommande,@PathVariable("idLigneCommande")Integer idLigneCommande,@PathVariable("idArticle")Integer idArticle);

    @DeleteMapping(value = COMMANDE_FOURNISSEUR_ENDPOINT + "/delete/article/{idCommande}/{idLigneCommande}" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE )
    CommandeFournisseurDto deleteArticle(@PathVariable("idCommande")Integer idCommande,@PathVariable("idLigneCommande")Integer idLigneCommande);

    @GetMapping(value = COMMANDE_FOURNISSEUR_ENDPOINT+ "/LignesCommande/{idCommande}")
    List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(@PathVariable("idCommande")Integer idCommande);

    @PostMapping(value = CREATE_COMMANDE_FOURNISSEUR_ENDPOINT , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE )
    @ApiOperation(value = "Enregistrer une commande fournisseur (ajouter/modifier)" ,notes = "cette methode permet d'enregistrer ou modifier une commande fournisseur ",response = CommandeFournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "l'objet commandefournisseur creer / modifier"),
            @ApiResponse( code = 400 , message = "l'objet commandefournisseur n'est pas valide")
    })
    CommandeFournisseurDto save(@RequestBody CommandeFournisseurDto dto);


    @GetMapping(value = FIND_COMMANDE_FOURNISSEUR_BY_ID_ENDPOINT , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher une commande fournisseur par ID" ,notes = "cette methode permet de rechercher une commande fournisseur par son ID",response = CommandeFournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "la commande fournisseur a ete trouver dans la BD"),
            @ApiResponse(code = 404 , message = "aucune commande fournisseur n'existe dans la BD avec ID fourni")
    })
    CommandeFournisseurDto findById(@PathVariable("idCommandeFournisseur")Integer id);


    @GetMapping(value = FIND_COMMANDE_FOURNISSEUR_BY_CODE_ENDPOINT , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher une commande fournisseur par code " ,notes = "cette methode permet de rechercher une commande fournisseur par son code ",response = CommandeFournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "la commande fournisseur a ete trouver dans la BD"),
            @ApiResponse(code = 404 , message = "aucune commande fournisseur n'existe dans la BD avec code categorie fourni")
    })
    CommandeFournisseurDto findByCode(@PathVariable("codeCommandeFournisseur")String code);



    @GetMapping(value = FIND_ALL_COMMANDE_FOURNISSEUR_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "renvoie la listes des commande fournisseur dans BD" ,notes = "cette methode permet de rechercher et renvoyer la liste des commandes fournisseur dans la BD",responseContainer ="List<CommandeFournisseurDto>")
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "la liste des commandefournisseur / une liste vide"),
    })
    List<CommandeFournisseurDto> findAll();



    @DeleteMapping(value = DELETE_COMMANDE_FOURNISSEUR_ENDPOINT)
    @ApiOperation(value = "supprimer une commande fournisseur par idCommandeFournisseur" ,notes = "cette methode permet de supprimer une commande fournisseur par son id",response = CommandeFournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "la commande fournisseur a ete supprimer de la BD"),
    })
    void delete(@PathVariable("idCommandeFournisseur")Integer id);
}
