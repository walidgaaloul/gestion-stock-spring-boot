package com.gaaloul.gestiondestock.dto;

import com.gaaloul.gestiondestock.model.Utilisateur;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class UtilisateurDto {
    private Integer id;

    private String nom;

    private String prenom;

    private String email;

    private Instant dateDeNaissance;

    private String motDePasse;

    private AdresseDto adresse;

    private String photo;


    private EntrepriseDto entreprise;

    private List<RolesDto> roles;

    private Integer idEntreprise;

    public static UtilisateurDto fromEntity(Utilisateur utilisateur){
        if (utilisateur == null){
            return null;
        }
        return UtilisateurDto.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .email(utilisateur.getEmail())
                .dateDeNaissance(utilisateur.getDateDeNaissance())
                .motDePasse(utilisateur.getMotDePasse())
                .photo(utilisateur.getPhoto())
                .adresse(AdresseDto.fromEntity(utilisateur.getAdresse()))
                .entreprise(EntrepriseDto.fromEntity(utilisateur.getEntreprise()))
                .roles(utilisateur.getRoles() != null  ?
                        utilisateur.getRoles().stream()
                                .map(RolesDto::fromEntity )
                                .collect(Collectors.toList()) : null)
                .build();
    }

    public static Utilisateur toEntity(UtilisateurDto utilisateurDto){
        if (utilisateurDto == null){
            return  null;
        }
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(utilisateurDto.getId());
        utilisateur.setNom(utilisateurDto.getNom());
        utilisateur.setPrenom(utilisateurDto.getPrenom());
        utilisateur.setEmail(utilisateurDto.getEmail());
        utilisateur.setDateDeNaissance(utilisateurDto.getDateDeNaissance());
        utilisateur.setMotDePasse(utilisateurDto.getMotDePasse());
        utilisateur.setPhoto(utilisateurDto.getPhoto());
        utilisateur.setEntreprise(EntrepriseDto.toEntity(utilisateurDto.getEntreprise()));



        return utilisateur;
    }


}
