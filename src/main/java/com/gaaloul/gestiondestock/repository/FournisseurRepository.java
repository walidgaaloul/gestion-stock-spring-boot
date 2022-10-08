package com.gaaloul.gestiondestock.repository;

import com.gaaloul.gestiondestock.model.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FournisseurRepository extends JpaRepository<Fournisseur,Integer> {
}
