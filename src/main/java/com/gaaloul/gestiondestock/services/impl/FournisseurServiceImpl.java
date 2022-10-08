package com.gaaloul.gestiondestock.services.impl;

import com.gaaloul.gestiondestock.dto.FournisseurDto;
import com.gaaloul.gestiondestock.exception.EntityNotFoundException;
import com.gaaloul.gestiondestock.exception.ErrorCodes;
import com.gaaloul.gestiondestock.exception.InvalideEntityException;
import com.gaaloul.gestiondestock.exception.InvalideOperationException;
import com.gaaloul.gestiondestock.model.CommandeClient;
import com.gaaloul.gestiondestock.model.CommandeFournisseur;
import com.gaaloul.gestiondestock.model.LigneCommandeClient;
import com.gaaloul.gestiondestock.model.LigneCommandeFournisseur;
import com.gaaloul.gestiondestock.repository.CommandeFournisseurRepository;
import com.gaaloul.gestiondestock.repository.FournisseurRepository;
import com.gaaloul.gestiondestock.services.FournisseurService;
import com.gaaloul.gestiondestock.validator.FournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FournisseurServiceImpl implements FournisseurService {
    private FournisseurRepository fournisseurRepository;
    private CommandeFournisseurRepository commandeFournisseurRepository;

    public FournisseurServiceImpl(FournisseurRepository fournisseurRepository, CommandeFournisseurRepository commandeFournisseurRepository) {
        this.fournisseurRepository = fournisseurRepository;
        this.commandeFournisseurRepository = commandeFournisseurRepository;
    }

    @Override
    public FournisseurDto save(FournisseurDto dto) {
        List<String>errors = FournisseurValidator.validate(dto);
        if (!errors.isEmpty()){
            log.error("Fournisseur is not valid{}",dto);
            throw new InvalideEntityException("Le fournisseur n'est pas valide", ErrorCodes.FOURNISSEUR_NOT_VALID,errors);
        }

        return FournisseurDto.fromEntity(fournisseurRepository.save(FournisseurDto.toEntity(dto)));
    }

    @Override
    public FournisseurDto findById(Integer id) {
        if (id==null){
            log.error("Fournisseur ID is null");
            return null;
        }

        return fournisseurRepository.findById(id).map(FournisseurDto::fromEntity).orElseThrow(()->
                new EntityNotFoundException("aucun Fournisseur avec ID:"+id+"n'as été trouver",ErrorCodes.FOURNISSEUR_NOT_FOUND));
    }

    @Override
    public List<FournisseurDto> findAll() {
        return fournisseurRepository.findAll().stream().map(FournisseurDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id==null){
            return;
        }
        List<CommandeFournisseur> commandeFournisseurs = commandeFournisseurRepository.findAllByFournisseurId(id);
        if (!commandeFournisseurs.isEmpty()){
            throw new InvalideOperationException("impossible de supprimer ce Fournisseur deja utiliser ",ErrorCodes.FOURNISSEUR_ALREADY_IN_USE);

        }
        fournisseurRepository.deleteById(id);

    }
}
