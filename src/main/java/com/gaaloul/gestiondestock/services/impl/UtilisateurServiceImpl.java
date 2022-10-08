package com.gaaloul.gestiondestock.services.impl;

import com.gaaloul.gestiondestock.dto.ChangerMotDePasseUtilisateurDto;
import com.gaaloul.gestiondestock.dto.UtilisateurDto;
import com.gaaloul.gestiondestock.exception.EntityNotFoundException;
import com.gaaloul.gestiondestock.exception.ErrorCodes;
import com.gaaloul.gestiondestock.exception.InvalideEntityException;
import com.gaaloul.gestiondestock.exception.InvalideOperationException;
import com.gaaloul.gestiondestock.model.Utilisateur;
import com.gaaloul.gestiondestock.repository.UtilisateurRepository;
import com.gaaloul.gestiondestock.services.UtilisateurService;
import com.gaaloul.gestiondestock.validator.UtilisateurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UtilisateurServiceImpl implements UtilisateurService {
    private UtilisateurRepository utilisateurRepository;

    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UtilisateurDto save(UtilisateurDto dto) {
        List<String> errors = UtilisateurValidator.validate(dto);
        if (!errors.isEmpty()){
            log.error("Utilisateur is not valid{}",dto);
            throw new InvalideEntityException("L'Utilisateur n'est pas valid", ErrorCodes.UTILISATEUR_NOT_VALID,errors);
        }
        return UtilisateurDto.fromEntity(utilisateurRepository.save(UtilisateurDto.toEntity(dto)));
    }

    @Override
    public UtilisateurDto findById(Integer id) {
        if (id== null){
            log.error("Utilisateur ID is null");
            return null;
        }
        return utilisateurRepository.findById(id).map(UtilisateurDto::fromEntity).orElseThrow(()->
                new EntityNotFoundException("Aucun Utilisateur avec ID" + id + "n'as été trouver dans DB",ErrorCodes.UTILISATEUR_NOT_FOUND));
    }

    @Override
    public List<UtilisateurDto> findAll() {
        return utilisateurRepository.findAll().stream().map(UtilisateurDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id==null){
            return;
        }

        utilisateurRepository.deleteById(id);

    }

    @Override
    public UtilisateurDto findByEmail(String email) {
        return utilisateurRepository.findUtilisateurByEmail(email)
                .map(UtilisateurDto::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException(
                        "Aucun utilisateur avec email= " + email + "n'été trouver dals la BD",
                        ErrorCodes.UTILISATEUR_NOT_FOUND
                ));
    }

    @Override
    public UtilisateurDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto) {

        validate(dto);
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(dto.getId());
        if (utilisateurOptional.isEmpty()){
            log.warn("aucune utilisateurOptional n'a ete trouver avec ID"+ dto.getId());
            throw new EntityNotFoundException("aucune utilisateurOptional n'a ete trouver avec ID"+ dto.getId(),ErrorCodes.UTILISATEUR_NOT_FOUND);
        }
        Utilisateur utilisateur = utilisateurOptional.get();
        utilisateur.setMotDePasse(dto.getMotDePasse());
        return UtilisateurDto.fromEntity(utilisateurRepository.save(utilisateur));
    }

    private void validate(ChangerMotDePasseUtilisateurDto dto){

        if (dto == null){
            log.warn("impossible de changer le mot de passe avec un objet null");
            throw new InvalideOperationException("aucune information n a ete fournie pour pouvoir changer le mot de passe"
                    ,ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
        if (dto.getId() == null){
            log.warn("impossible de changer le mot de passe avec un ID null");
            throw new InvalideOperationException("ID utilisateur null impossible de modifier le mot de passe"
                    ,ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);

        }

        if (!StringUtils.hasLength(dto.getMotDePasse()) || !StringUtils.hasLength(dto.getConfirmMotDePasse())){
            log.warn("impossible de changer le mot de passe avec un mot de passe null");
            throw new InvalideOperationException("mot de passe utilisateur null impossible de modifier le mot de passe"
                    ,ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);

        }

        if (!dto.getMotDePasse().equals(dto.getConfirmMotDePasse())){
            log.warn("impossible de changer le mot de passe avec un mot de passe comfirm different de mot de passe");
            throw new InvalideOperationException("mot de passe utilisateur non confirm impossible de modifier le mot de passe"
                    ,ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);

        }
    }
}
