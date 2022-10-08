package com.gaaloul.gestiondestock.repository;

import com.gaaloul.gestiondestock.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientRepository extends JpaRepository< Client,Integer> {
}
