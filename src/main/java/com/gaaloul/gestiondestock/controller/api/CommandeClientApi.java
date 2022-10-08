package com.gaaloul.gestiondestock.controller.api;

import com.gaaloul.gestiondestock.dto.CategoryDto;
import com.gaaloul.gestiondestock.dto.ClientDto;
import com.gaaloul.gestiondestock.dto.CommandeClientDto;
import com.gaaloul.gestiondestock.dto.LigneCommandeClientDto;
import com.gaaloul.gestiondestock.model.EtatCommande;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.gaaloul.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT+"/commandeclients")
public interface CommandeClientApi {

    @PatchMapping(value = APP_ROOT + "/commandeclients/update/etat/{idCommande}/{etatCommande}" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE )
    @ApiOperation(value = "Enregistrer une commande client (ajouter/modifier)" ,notes = "cette methode permet d'enregistrer ou modifier une commande clients ",response = CommandeClientDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "l'objet commandeclient creer / modifier"),
            @ApiResponse( code = 400 , message = "l'objet commandeclient n'est pas valide")
    })
    ResponseEntity<CommandeClientDto> updateEtatCommande(@PathVariable("idCommande") Integer idCommande ,@PathVariable("etatCommande") EtatCommande etatCommande);


    @PatchMapping(value = APP_ROOT + "/commandeclients/update/quantite/{idCommande}/{idLigneCommande}/{quantite}" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE )
    ResponseEntity<CommandeClientDto>  updateQuantiteCommande(@PathVariable("idCommande")Integer idCommande,@PathVariable("idLigneCommande") Integer idLigneCommande,@PathVariable("quantite") BigDecimal quantite);



    @PatchMapping(value = APP_ROOT + "/commandeclients/update/client/{idCommande}/{idClient}" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE )
    ResponseEntity<CommandeClientDto> updateClient(@PathVariable("idCommande")Integer idCommande,@PathVariable("idClient") Integer idClient);

    @PatchMapping(value = APP_ROOT + "/commandeclients/update/article/{idCommande}/{idLigneCommande}/{idArticle}" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE )
    ResponseEntity<CommandeClientDto> updateArticle(@PathVariable("idCommande")Integer idCommande,@PathVariable("idLigneCommande")Integer idLigneCommande,@PathVariable("newIdArticle")Integer newIdArticle);

    @DeleteMapping(value = APP_ROOT + "/commandeclients/delete/article/{idCommande}/{idLigneCommande}" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE )
    ResponseEntity<CommandeClientDto> deleteArticle(@PathVariable("idCommande")Integer idCommande,@PathVariable("idLigneCommande")Integer idLigneCommande);

    @PostMapping(value = APP_ROOT + "/commandeclients/create" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE )
    @ApiOperation(value = "Enregistrer une commande client (ajouter/modifier)" ,notes = "cette methode permet d'enregistrer ou modifier une commande clients ",response = CommandeClientDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "l'objet commandeclient creer / modifier"),
            @ApiResponse( code = 400 , message = "l'objet commandeclient n'est pas valide")
    })
    ResponseEntity<CommandeClientDto> save(@RequestBody CommandeClientDto dto);

    @GetMapping(value = APP_ROOT+ "/commandeclients/{idCommandeClient}" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher une commande client par ID" ,notes = "cette methode permet de rechercher une commande client par son ID",response = CommandeClientDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "la commande client a ete trouver dans la BD"),
            @ApiResponse(code = 404 , message = "aucune commande client n'existe dans la BD avec ID fourni")
    })
    ResponseEntity<CommandeClientDto> findById(@PathVariable Integer idCommandeClient);


    @GetMapping(value = APP_ROOT+ "/commandeclients/{codeCommandeClient}" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "rechercher une commande client par code " ,notes = "cette methode permet de rechercher une commandeclient par son code ",response = CommandeClientDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "la commandeclient a ete trouver dans la BD"),
            @ApiResponse(code = 404 , message = "aucune commandeclient n'existe dans la BD avec code categorie fourni")
    })
    ResponseEntity<CommandeClientDto> findByCode(@PathVariable("codeCommandeClient")String code);



    @GetMapping(value = APP_ROOT+ "/commandeclients/all" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "renvoie la listes des commande clients dans BD" ,notes = "cette methode permet de rechercher et renvoyer la liste des commandes clients dans la BD",responseContainer ="List<CommandeClientDto>")
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "la liste des commandeclients / une liste vide"),
    })
    ResponseEntity<List<CommandeClientDto>> findAll();



    @GetMapping(value = APP_ROOT+ "/commandeclients/LignesCommande/{idCommande}")
    ResponseEntity<List<LigneCommandeClientDto>> findAllLignesCommandesClientByCommandeClientId(@PathVariable("idCommande")Integer idCommande);

    @DeleteMapping(value = APP_ROOT+"/commandeclients/delete/{idCommandeClient}")
    @ApiOperation(value = "supprimer une commande client par idCommandeClient" ,notes = "cette methode permet de supprimer une commandeclient par son id",response = CommandeClientDto.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "la commandeclient a ete supprimer de la BD"),
    })
    ResponseEntity<Void> delete(@PathVariable("idCommandeClient")Integer id);
}
