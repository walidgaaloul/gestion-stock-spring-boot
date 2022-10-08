package com.gaaloul.gestiondestock.repository;

import com.gaaloul.gestiondestock.model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EntrepriseRepository extends JpaRepository<Entreprise,Integer> {
}
