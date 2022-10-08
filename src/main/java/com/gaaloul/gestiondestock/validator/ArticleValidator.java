package com.gaaloul.gestiondestock.validator;

import com.gaaloul.gestiondestock.dto.ArticleDto;
import com.gaaloul.gestiondestock.dto.CategoryDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ArticleValidator {
    public static List<String> validate(ArticleDto dto){
        List<String> errors = new ArrayList<>();

        if (dto == null){
            errors.add("veuillez renseigner le code de la article ");
            errors.add("veuillez renseigner le Designation de la article ");
            errors.add("veuillez renseigner le Prix Unitaire de la article ");
            errors.add("veuillez renseigner le Prix Unitaire Ttc de la article ");
            errors.add("veuillez renseigner le Taux Tva de la article ");
            errors.add("veuillez renseigner le  Categorie de la article ");
            return errors;
        }

        if (!StringUtils.hasLength(dto.getCodeArticle())){
            errors.add("veuillez renseigner le code de la article ");
        }
        if (!StringUtils.hasLength(dto.getDesignation())){
            errors.add("veuillez renseigner le Designation de la article ");
        }
        if (dto.getPrixUnitaireHt() == null){
            errors.add("veuillez renseigner le Prix Unitaire de la article ");
        }

        if (dto.getPrixUnitaireTtc() == null){
            errors.add("veuillez renseigner le Prix Unitaire Ttc de la article ");
        }

        if (dto.getTauxTva() == null){
            errors.add("veuillez renseigner le Taux Tva de la article ");
        }

        if (dto.getCategory() == null){
            errors.add("veuillez renseigner le  Categorie de la article ");
        }

        return errors;
    }
}
