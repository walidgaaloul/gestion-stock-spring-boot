package com.gaaloul.gestiondestock.repository;


import com.gaaloul.gestiondestock.model.LigneCommandeClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface LigneCommandeClientRepository extends JpaRepository<LigneCommandeClient, Integer> {

    List<LigneCommandeClient> findAllByCommandeClientId(Integer id);
    List<LigneCommandeClient>findAllByArticleId(Integer id);
}
