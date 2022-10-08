package com.gaaloul.gestiondestock.validator;

import com.gaaloul.gestiondestock.dto.UtilisateurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurValidator {
    public static List<String> validate (UtilisateurDto utilisateurDto){
        List<String> errors = new ArrayList<>();

        if (utilisateurDto == null){
            errors.add("veuillez renseigner le nom d'utilisateur ");
            errors.add("veuillez renseigner le prenom d'utilisateur ");
            errors.add("veuillez renseigner l'email' ");
            errors.add("veuillez renseigner le mot de passe ");
            errors.add("veuillez renseigner l'adresse''utilisateur ");
            errors.addAll(AdresseValidator.validate(null));
            return errors;
        }

        if(!StringUtils.hasLength(utilisateurDto.getNom())){
            errors.add("veuillez renseigner le nom d'utilisateur ");
        }
        if(!StringUtils.hasLength(utilisateurDto.getPrenom())){
            errors.add("veuillez renseigner le prenom d'utilisateur ");
        }
        if(utilisateurDto.getDateDeNaissance() == null){
            errors.add("veuillez renseigner la date de naissance d'utilisateur ");
        }
        if(!StringUtils.hasLength(utilisateurDto.getEmail())){
            errors.add("veuillez renseigner l'email' ");
        }
        if(!StringUtils.hasLength(utilisateurDto.getMotDePasse())){
            errors.add("veuillez renseigner le mot de passe ");
        }

        errors.addAll(AdresseValidator.validate(utilisateurDto.getAdresse()));
            return errors;
    }
}
