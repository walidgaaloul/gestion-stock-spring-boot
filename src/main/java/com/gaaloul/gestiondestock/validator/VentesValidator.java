package com.gaaloul.gestiondestock.validator;

import com.gaaloul.gestiondestock.dto.CommandeClientDto;
import com.gaaloul.gestiondestock.dto.VentesDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class VentesValidator {
    public static List<String> validate(VentesDto dto) {
        List<String> errors = new ArrayList<>();
         if (dto == null){
            errors.add("veuillez renseigner le code de la Vente ");
            errors.add("veuillez renseigner le date de la Vente ");

            return errors;
        }

        if (!StringUtils.hasLength(dto.getCode())){
            errors.add("veuillez renseigner le code de la Vente ");
        }

        if (dto.getDateVente() == null){
            errors.add("veuillez renseigner la Date de la Vente ");
        }


        return errors;
    }
}
