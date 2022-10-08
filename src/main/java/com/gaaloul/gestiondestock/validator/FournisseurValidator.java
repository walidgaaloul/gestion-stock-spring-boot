package com.gaaloul.gestiondestock.validator;

import com.gaaloul.gestiondestock.dto.ArticleDto;
import com.gaaloul.gestiondestock.dto.FournisseurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FournisseurValidator {
    public static List<String> validate(FournisseurDto dto) {
        List<String> errors = new ArrayList<>();
        if (dto == null){
            errors.add("veuillez renseigner le nom d'utilisateur ");
            errors.add("veuillez renseigner le prenom d'utilisateur ");
            errors.add("veuillez renseigner l'email' ");
            errors.add("veuillez renseigner le mot de passe ");
            errors.add("veuillez renseigner l'adresse''utilisateur ");
            errors.addAll(AdresseValidator.validate(null));
            return errors;
        }
        if(!StringUtils.hasLength(dto.getNom())){
            errors.add("veuillez renseigner le nom d'Fournisseur ");
        }
        if(!StringUtils.hasLength(dto.getPrenom())){
            errors.add("veuillez renseigner le prenom d'Fournisseur ");
        }

        if(!StringUtils.hasLength(dto.getMail())){
            errors.add("veuillez renseigner l'email d'Fournisseur ");
        }
        if(!StringUtils.hasLength(dto.getNumTel())){
            errors.add("veuillez renseigner NumTel");
        }

        errors.addAll(AdresseValidator.validate(dto.getAdresse()));
        return errors;
    }
    }
