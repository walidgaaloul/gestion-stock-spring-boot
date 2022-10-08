package com.gaaloul.gestiondestock.validator;

import com.gaaloul.gestiondestock.dto.ClientDto;
import com.gaaloul.gestiondestock.dto.EntrepriseDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class EntrepriseValidator {
    public static List<String> validate(EntrepriseDto dto){
        List<String> errors = new ArrayList<>();

        if (dto == null){
            errors.add("veuillez renseigner le Nom de l'entreprise ");
            errors.add("veuillez renseigner le Description de la entreprise ");
            errors.add("veuillez renseigner le email de la entreprise ");
            errors.add("veuillez renseigner le numtel de la entreprise ");
            errors.add("veuillez renseigner le CodeFiscal de la entreprise ");
            errors.addAll(AdresseValidator.validate(null));


            return errors;
        }

        if (!StringUtils.hasLength(dto.getNom())){
            errors.add("veuillez renseigner le Nom de l'entreprise ");
        }

        if (!StringUtils.hasLength(dto.getDescription())){
            errors.add("veuillez renseigner le Description de la entreprise ");
        }


        if (!StringUtils.hasLength(dto.getEmail())){
            errors.add("veuillez renseigner le email de la entreprise ");
        }

        if (!StringUtils.hasLength(dto.getNumTel())){
            errors.add("veuillez renseigner le numtel de la entreprise ");
        }

        if (!StringUtils.hasLength(dto.getCodeFiscal())){
            errors.add("veuillez renseigner le CodeFiscal de la entreprise ");
        }
        errors.addAll(AdresseValidator.validate(dto.getAdresse()));
            return errors;


    }

    }
