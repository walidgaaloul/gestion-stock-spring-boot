package com.gaaloul.gestiondestock.validator;

import com.gaaloul.gestiondestock.dto.CommandeClientDto;
import com.gaaloul.gestiondestock.dto.CommandeFournisseurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CommandeFournisseurValidator {
    public static List<String> validate(CommandeFournisseurDto dto) {
        List<String> errors = new ArrayList<>();
        if (dto == null){
            errors.add("veuillez renseigner le code de la commande ");
            errors.add("veuillez renseigner le date de la commande ");
            errors.add("veuillez renseigner l'Etat'de la commande ");
            errors.add("veuillez renseigner le Fournisseur de la commande ");
            return errors;
        }

        if (!StringUtils.hasLength(dto.getCode())){
            errors.add("veuillez renseigner le code de la commande ");
        }
        if (dto.getDateCommande() == null){
            errors.add("veuillez renseigner la Date de la commande ");
        }

        if (!StringUtils.hasLength(dto.getEtatCommande().toString())){
            errors.add("veuillez renseigner l'Etat'de la commande ");
        }

        if (dto.getFournisseur()== null || dto.getFournisseur().getId()== null){
            errors.add("veuillez renseigner le Fournisseur de la commande ");
        }
        return errors;
    }
}

