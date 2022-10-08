package com.gaaloul.gestiondestock.dto;

import com.gaaloul.gestiondestock.model.Fournisseur;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class FournisseurDto {
    private Integer id;

    private String nom;

    private String prenom;

    private AdresseDto adresse;

    private String  photo;

    private String  mail;

    private String  numTel;

    private Integer idEntreprise;

    private List<CommandeFournisseurDto> commandeFournisseurs;

    public static FournisseurDto fromEntity (Fournisseur fournisseur){
if (fournisseur == null){
    return null;
}
return FournisseurDto.builder()
        .id(fournisseur.getId())
        .nom(fournisseur.getNom())
        .photo(fournisseur.getPhoto())
        .mail(fournisseur.getMail())
        .numTel(fournisseur.getNumTel())
        .idEntreprise(fournisseur.getIdEntreprise())
        .build();

    }

    public static Fournisseur toEntity(FournisseurDto fournisseurDto){
        if(fournisseurDto == null){
            return null;
        }
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setId(fournisseurDto.getId());
        fournisseur.setNom(fournisseurDto.getNom());
        fournisseur.setPhoto(fournisseurDto.getPhoto());
        fournisseur.setMail(fournisseurDto.getMail());
        fournisseur.setNumTel(fournisseurDto.getNumTel());
        fournisseur.setIdEntreprise(fournisseurDto.getIdEntreprise());
        return fournisseur;
    }
}

