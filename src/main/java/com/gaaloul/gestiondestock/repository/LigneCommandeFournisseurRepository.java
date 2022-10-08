package com.gaaloul.gestiondestock.repository;

import com.gaaloul.gestiondestock.model.LigneCommandeClient;
import com.gaaloul.gestiondestock.model.LigneCommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LigneCommandeFournisseurRepository extends JpaRepository<LigneCommandeFournisseur,Integer> {
    List<LigneCommandeFournisseur> findAllByCommandeFournisseurId(Integer id);
    List<LigneCommandeFournisseur> findAllByArticleId(Integer id);


}
