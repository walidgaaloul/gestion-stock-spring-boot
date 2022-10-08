package com.gaaloul.gestiondestock.validator;

import com.gaaloul.gestiondestock.dto.ArticleDto;
import com.gaaloul.gestiondestock.dto.ClientDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {
    public static List<String> validate(ClientDto dto){
        List<String> errors = new ArrayList<>();
        if (dto == null){
            errors.add("veuillez renseigner le Nom de la client ");
            errors.add("veuillez renseigner le Prenom de la client ");
            errors.add("veuillez renseigner le EMail de la client ");
            errors.add("veuillez renseigner le NumTel de la client ");
            errors.addAll(AdresseValidator.validate(null));

return errors;

        }

        if (!StringUtils.hasLength(dto.getNom())){
            errors.add("veuillez renseigner le Nom de la client ");
        }
        if (!StringUtils.hasLength(dto.getPrenom())){
            errors.add("veuillez renseigner le Prenom de la client ");
        }
        if (!StringUtils.hasLength(dto.getMail())){
            errors.add("veuillez renseigner le EMail de la client ");
        }
        if (!StringUtils.hasLength(dto.getNumTel())){
            errors.add("veuillez renseigner le NumTel de la client ");
        }


        errors.addAll(AdresseValidator.validate(dto.getAdresse()));
        return errors;
    }
}
