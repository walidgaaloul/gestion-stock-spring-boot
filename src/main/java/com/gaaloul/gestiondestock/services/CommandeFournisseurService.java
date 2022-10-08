package com.gaaloul.gestiondestock.services;

import com.gaaloul.gestiondestock.dto.CommandeClientDto;
import com.gaaloul.gestiondestock.dto.CommandeFournisseurDto;
import com.gaaloul.gestiondestock.dto.LigneCommandeClientDto;
import com.gaaloul.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.gaaloul.gestiondestock.model.EtatCommande;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public interface CommandeFournisseurService {
    CommandeFournisseurDto save(CommandeFournisseurDto dto);
    CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande);

    CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite);

    CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur);

    CommandeFournisseurDto updateArticle(Integer idCommande,Integer idLigneCommande,Integer idArticle);

    CommandeFournisseurDto deleteArticle(Integer idCommande,Integer idLigneCommande);


    CommandeFournisseurDto findById(Integer id);
    CommandeFournisseurDto findByCode(String code);
    List<CommandeFournisseurDto> findAll();

    List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(Integer idCommande);
    void delete(Integer id);
}
