package com.gaaloul.gestiondestock.validator;

import com.gaaloul.gestiondestock.dto.MvtStkDto;
import com.gaaloul.gestiondestock.dto.VentesDto;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MvtStkValidator {
    public static List<String> validate(MvtStkDto dto) {
        List<String> errors = new ArrayList<>();

        if(dto == null){
            errors.add("veuillez renseigner la Date du mouvement ");
            errors.add("veuillez renseigner la Quantite du mouvement ");
            errors.add("veuillez renseigner l'Article ");
            errors.add("veuillez renseigner le type de mouvement' ");

            return errors;
        }

        if (dto.getDateMvt()== null){
            errors.add("veuillez renseigner la Date du mouvement ");
        }

        if (dto.getQuantite()== null || dto.getQuantite().compareTo(BigDecimal.ZERO)==0){
            errors.add("veuillez renseigner la Quantite du mouvement ");
        }

        if (dto.getArticle()== null || dto.getArticle().getId()== null){
            errors.add("veuillez renseigner l'Article ");
        }

        if (!StringUtils.hasLength(dto.getTypeMvt().name())){
            errors.add("veuillez renseigner le type de mouvement' ");
        }
        return errors;
    }
}
