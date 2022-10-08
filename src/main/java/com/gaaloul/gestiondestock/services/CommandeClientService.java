package com.gaaloul.gestiondestock.services;

import com.gaaloul.gestiondestock.dto.CommandeClientDto;
import com.gaaloul.gestiondestock.dto.LigneCommandeClientDto;
import com.gaaloul.gestiondestock.model.EtatCommande;

import java.math.BigDecimal;
import java.util.List;

public interface CommandeClientService {
    CommandeClientDto save(CommandeClientDto dto);
    CommandeClientDto updateEtatCommande(Integer idCommande , EtatCommande etatCommande);
    CommandeClientDto updateQuantiteCommande(Integer idCommande ,Integer idLigneCommande, BigDecimal quantite);
    CommandeClientDto updateClient(Integer idCommande ,Integer idClient);

    CommandeClientDto updateArticle(Integer idCommande,Integer idLigneCommande,Integer newIdArticle);

    //delete article ==> delete ligne commande client
    CommandeClientDto deleteArticle(Integer idCommande,Integer idLigneCommande);

    CommandeClientDto findById(Integer id);
    CommandeClientDto findByCode(String code);
    List<CommandeClientDto> findAll();
    void delete(Integer id);

    List<LigneCommandeClientDto> findAllLignesCommandesClientByCommandeClientId(Integer idCommande);
}
