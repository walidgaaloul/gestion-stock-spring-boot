package com.gaaloul.gestiondestock.repository;

import com.gaaloul.gestiondestock.model.LigneVente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LigneVenteRepository extends JpaRepository<LigneVente,Integer> {
    List<LigneVente> findAllByArticleId(Integer idArticle);

    List<LigneVente> findAllByCommandeVenteId(Integer id);
}

