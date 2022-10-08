package com.gaaloul.gestiondestock.dto;

import com.gaaloul.gestiondestock.model.Adresse;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AdresseDto {

    private Integer id;


    private String adresse1;

    private String adresse2;

    private String ville;

    private String codepostale;

    private String pays;

    public static AdresseDto fromEntity(Adresse adresse){
        if(adresse == null){
            return null;
        }
        return AdresseDto.builder()
                .adresse1(adresse.getAdresse1())
                .adresse2(adresse.getAdresse2())
                .codepostale(adresse.getCodepostale())
                .ville(adresse.getVille())
                .pays(adresse.getPays())
                .build();
    }
    public static Adresse toEntity(AdresseDto adresseDto){
        if(adresseDto == null){
            return null;
        }
        Adresse adresse = new Adresse();
        adresse.setAdresse1(adresseDto.getAdresse1());
        adresse.setAdresse2(adresseDto.getAdresse2());
        adresse.setCodepostale(adresseDto.getCodepostale());
        adresse.setVille(adresseDto.getVille());
        adresse.setPays(adresseDto.getPays());
        return adresse;
    }
        
}
