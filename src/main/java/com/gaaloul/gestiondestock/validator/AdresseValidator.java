package com.gaaloul.gestiondestock.validator;

import com.gaaloul.gestiondestock.dto.AdresseDto;
import com.gaaloul.gestiondestock.dto.ClientDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AdresseValidator {
    public static List<String> validate(AdresseDto adresseDto){
        List<String> errors = new ArrayList<>();
        if (adresseDto == null){
            errors.add("veuillez renseigner l'adresse1' ");
            errors.add("veuillez renseigner le pay' ");
            errors.add("veuillez renseigner la ville' ");
            errors.add("veuillez renseigner le Code postale' ");

            return errors;
        }

        if (!StringUtils.hasLength(adresseDto.getAdresse1())){
            errors.add("veuillez renseigner l'adresse1' ");
            return errors;
        }

        if (!StringUtils.hasLength(adresseDto.getPays())){
            errors.add("veuillez renseigner le pay' ");
            return errors;
        }

        if (!StringUtils.hasLength(adresseDto.getVille())){
            errors.add("veuillez renseigner la ville' ");
            return errors;
        }

        if (!StringUtils.hasLength(adresseDto.getCodepostale())){
            errors.add("veuillez renseigner le Code postale' ");
            return errors;
        }
        return errors;
    }
}
