package com.gaaloul.gestiondestock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gaaloul.gestiondestock.model.LigneCommandeClient;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class LigneCommandeClientDto {
    private Integer id;


    private ArticleDto article;

@JsonIgnore
    private CommandeClientDto commandeclient;


    private BigDecimal quantite;


    private BigDecimal prixUnitaire;

    private Integer idEntreprise;

    private Integer idCommande;



    public static LigneCommandeClientDto fromEntity(LigneCommandeClient ligneCommandeClient){
        if (ligneCommandeClient == null){
            return null;
        }
        return LigneCommandeClientDto.builder()
                .id(ligneCommandeClient.getId())
                .article(ArticleDto.fromEntity(ligneCommandeClient.getArticle()))
                .prixUnitaire(ligneCommandeClient.getPrixUnitaire())
                .quantite(ligneCommandeClient.getQuantite())
                .idEntreprise(ligneCommandeClient.getIdEntreprise())
                .commandeclient(CommandeClientDto.fromEntity(ligneCommandeClient.getCommandeClient()))
                .build();
    }

    public static LigneCommandeClient toEntity(LigneCommandeClientDto ligneCommandeClientDto){
        if (ligneCommandeClientDto == null){
            return null;
        }
        LigneCommandeClient ligneCommandeClient= new LigneCommandeClient();
        ligneCommandeClient.setId(ligneCommandeClientDto.getId());
        ligneCommandeClient.setArticle(ArticleDto.toEntity(ligneCommandeClientDto.getArticle()));
        ligneCommandeClient.setPrixUnitaire(ligneCommandeClientDto.getPrixUnitaire());
        ligneCommandeClient.setQuantite(ligneCommandeClientDto.getQuantite());
        ligneCommandeClient.setIdEntreprise(ligneCommandeClientDto.getIdEntreprise());
        ligneCommandeClient.setCommandeClient(CommandeClientDto.toEntity(ligneCommandeClientDto.getCommandeclient()));
        return ligneCommandeClient;
    }
}
