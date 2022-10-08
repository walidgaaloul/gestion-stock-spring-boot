package com.gaaloul.gestiondestock.services.impl;

import com.gaaloul.gestiondestock.dto.*;
import com.gaaloul.gestiondestock.exception.EntityNotFoundException;
import com.gaaloul.gestiondestock.exception.ErrorCodes;
import com.gaaloul.gestiondestock.exception.InvalideEntityException;
import com.gaaloul.gestiondestock.exception.InvalideOperationException;
import com.gaaloul.gestiondestock.model.*;
import com.gaaloul.gestiondestock.repository.ArticleRepository;
import com.gaaloul.gestiondestock.repository.CommandeFournisseurRepository;
import com.gaaloul.gestiondestock.repository.FournisseurRepository;
import com.gaaloul.gestiondestock.repository.LigneCommandeFournisseurRepository;
import com.gaaloul.gestiondestock.services.CommandeFournisseurService;
import com.gaaloul.gestiondestock.services.MvtStkService;
import com.gaaloul.gestiondestock.validator.ArticleValidator;
import com.gaaloul.gestiondestock.validator.CommandeFournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService {
    private CommandeFournisseurRepository commandeFournisseurRepository;
    private FournisseurRepository fournisseurRepository;
    private ArticleRepository articleRepository;
    private LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;

    private MvtStkService mvtStkService;
    @Autowired
    public CommandeFournisseurServiceImpl(CommandeFournisseurRepository commandeFournisseurRepository, FournisseurRepository fournisseurRepository, ArticleRepository articleRepository, LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository, MvtStkService mvtStkService) {
        this.commandeFournisseurRepository = commandeFournisseurRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.articleRepository = articleRepository;
        this.ligneCommandeFournisseurRepository = ligneCommandeFournisseurRepository;
        this.mvtStkService = mvtStkService;
    }

    @Override
    public CommandeFournisseurDto save(CommandeFournisseurDto dto) {
        List<String> errors = CommandeFournisseurValidator.validate(dto);
        if (!errors.isEmpty()){
            log.error("commande Fournisseur n'est pas valide");
            throw new InvalideEntityException("commande client n'est pas valide", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_VALID,errors);
        }
        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(dto.getFournisseur().getId());
        if (!fournisseur.isPresent()){
            log.warn("fournisseur with ID{} was not found in the DB",dto.getFournisseur().getId());
            throw new EntityNotFoundException("Aucun fournisseur avec ID"+dto.getFournisseur().getId()+"n'as été trouver",ErrorCodes.FOURNISSEUR_NOT_FOUND);
        }
        List<String> articleErrors=new ArrayList<>();
        if (dto.getLigneCommandeFournisseurs() != null){
            dto.getLigneCommandeFournisseurs().forEach(LigCmdFrn ->{
                if (LigCmdFrn.getArticle() != null){
                    Optional<Article> article =articleRepository.findById(LigCmdFrn.getArticle().getId());
                    if (article.isEmpty()){
                        articleErrors.add("l'article avec ID"+LigCmdFrn.getArticle().getId()+"n'existe pas");

                    }
                }else {
                    articleErrors.add("impossible d'enregistrer une commande avec un article null");
                }
            });
        }

        if (!articleErrors.isEmpty()){
            log.warn("");
            throw new InvalideEntityException("Article n'existe pas dans la BD",ErrorCodes.COMMANDE_FOURNISSEUR_NOT_VALID,articleErrors);
        }
        CommandeFournisseur saveCmdFrn = commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(dto));
        if (dto.getLigneCommandeFournisseurs() != null){
            dto.getLigneCommandeFournisseurs().forEach(LigCmdFrn -> {
                LigneCommandeFournisseur ligneCommandeFournisseur = LigneCommandeFournisseurDto.toEntity(LigCmdFrn);
                ligneCommandeFournisseur.setCommandeFournisseur(saveCmdFrn);
                ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);
            });
        }
        return CommandeFournisseurDto.fromEntity(saveCmdFrn);
    }

    @Override
    public CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        checkIdCommande(idCommande);

        if (!StringUtils.hasLength(String.valueOf(etatCommande))){
            log.error("etat de la commande is null");
            throw new InvalideOperationException("impossible de modifier etat de la commande avec etat de la commande null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);

        }
        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
        commandeFournisseur.setEtatCommande(etatCommande);
        CommandeFournisseur savedCommande = commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseur)
        );
        if (commandeFournisseur.isCommandeLivree()){
            updateMvtStk(idCommande);}
        return CommandeFournisseurDto.fromEntity(savedCommande);
    }

    @Override
    public CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        checkIdCommande(idCommande);

        checkIdLigneCommande(idLigneCommande);

        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0){
            log.error("ID de la ligne  de la commande is null");
            throw new InvalideOperationException("impossible de modifier quantiter  avec une quantiter null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);

        }

        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
        Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOptional = findLigneCommandeFournisseur(idLigneCommande);
        LigneCommandeFournisseur ligneCommandeFournisseur = ligneCommandeFournisseurOptional.get();
        ligneCommandeFournisseur.setQuantite(quantite);
        ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);
        return commandeFournisseur;
    }

    @Override
    public CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur) {
        checkIdCommande(idCommande);

        checkIdClient(idFournisseur);

        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);

        Optional<Fournisseur> fournisseurOptional = fournisseurRepository.findById(idFournisseur);
        if (fournisseurOptional.isEmpty()){
            throw new EntityNotFoundException("Aucun client avec ID"+idFournisseur+"n'as été trouver dans DB",ErrorCodes.FOURNISSEUR_NOT_FOUND);
        }

        commandeFournisseur.setFournisseur(FournisseurDto.fromEntity(fournisseurOptional.get()));
        return CommandeFournisseurDto.fromEntity(commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseur)));
    }

    @Override
    public CommandeFournisseurDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        checkIdCommande(idCommande);

        checkIdLigneCommande(idLigneCommande);

        checkIdArticle(idArticle, "nouvel");

        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);

        Optional<LigneCommandeFournisseur> ligneCommandeFournisseur = findLigneCommandeFournisseur(idLigneCommande);

        Optional<Article> articleOptional =articleRepository.findById(idArticle);

        if (articleOptional.isEmpty()){
            throw new EntityNotFoundException("Aucun article avec ID"+idArticle+"n'as été trouver dans DB",ErrorCodes.ARTICLE_NOT_FOUND);

        }

        List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(articleOptional.get()));
        if (!errors.isEmpty()){
            throw new InvalideEntityException("article invalide",ErrorCodes.ARTICLE_NOT_VALID,errors);
        }

        LigneCommandeFournisseur ligneCommandeFournisseurToSaved = ligneCommandeFournisseur.get();
        ligneCommandeFournisseurToSaved.setArticle(articleOptional.get());
        ligneCommandeFournisseurRepository.save(ligneCommandeFournisseurToSaved);
        return commandeFournisseur;
    }

    @Override
    public CommandeFournisseurDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        //just check LigneCommandeClient and inform the client in case it is absent
        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
        findLigneCommandeFournisseur(idLigneCommande);
        ligneCommandeFournisseurRepository.deleteById(idLigneCommande);


        return commandeFournisseur;
    }

    @Override
    public List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(Integer idCommande) {
        return ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(idCommande).stream()
                .map(LigneCommandeFournisseurDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public CommandeFournisseurDto findById(Integer id) {
        if (id == null){
            log.error("Commande Fournisseur ID is null");
            return null;
        }

        return commandeFournisseurRepository.findById(id).map(CommandeFournisseurDto::fromEntity).orElseThrow(()->

        new EntityNotFoundException("Aucun Commande Fournisseur avec ID"+id+"n'as été trouver dans DB",ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND));
    }

    @Override
    public CommandeFournisseurDto findByCode(String code) {
        if (!StringUtils.hasLength(code)){
            log.error("Commande Fournisseur code is null");
            return null;
        }
        return commandeFournisseurRepository.findCommandeFournisseurByCode(code).map(CommandeFournisseurDto::fromEntity).orElseThrow(()->
                new EntityNotFoundException("Aucun Commande Fournisseur avec ID"+code+"n'as été trouver dans DB",ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND));
    }

    @Override
    public List<CommandeFournisseurDto> findAll() {
        return commandeFournisseurRepository.findAll().stream().map(CommandeFournisseurDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null){
            log.error("Commande client ID is null");
            return;
        }
        List<LigneCommandeFournisseur> ligneCommandeFournisseurs = ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(id);
        if (!ligneCommandeFournisseurs.isEmpty()){
            throw new InvalideOperationException("impossible de supprimer ce Commande Fournisseur deja utiliser ",ErrorCodes.COMMANDE_FOURNISSEUR_ALREADY_IN_USE);

        }
        commandeFournisseurRepository.deleteById(id);

    }

    private void checkIdCommande(Integer idCommande){

        if (idCommande == null){
            log.error("Commande client ID is null");
            throw new InvalideOperationException("impossible de modifier etat de la commande avec in ID null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);

        }
    }

    private void checkIdLigneCommande(Integer idLigneCommande ){
        if (idLigneCommande == null){
            log.error("ID de la ligne  de la commande is null");
            throw new InvalideOperationException("impossible de modifier Article  avec ID de la ligne  de la commande null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);

        }

    }

    private void checkIdClient(Integer idFournisseur ){
        if (idFournisseur == null){
            log.error("ID de la client  de la commande is null");
            throw new InvalideOperationException("impossible de modifier Fournisseur  avec ID Fournisseur null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);

        }
    }

    private void checkIdArticle(Integer idArticle , String msg ){
        if (idArticle == null){
            log.error("ID de "+msg+" Article is null");
            throw new InvalideOperationException("impossible de modifier Article  avec "+msg+" ID Article null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);

        }

    }

    private CommandeFournisseurDto checkEtatCommande(Integer idCommande) {
        CommandeFournisseurDto commandeFournisseur = findById(idCommande);
        if (commandeFournisseur.isCommandeLivree()){
            throw new InvalideOperationException("impossible de modifier la commande lorsqu'elle est livree",ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);

        }
        return commandeFournisseur;
    }

    private Optional<LigneCommandeFournisseur> findLigneCommandeFournisseur(Integer idLigneCommande) {
        Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOptional= ligneCommandeFournisseurRepository.findById(idLigneCommande);

        if (ligneCommandeFournisseurOptional.isEmpty()){
            throw new EntityNotFoundException("Aucun Ligne Commande Fournisseur avec ID"+idLigneCommande+"n'as été trouver dans DB",ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND);
        }
        return ligneCommandeFournisseurOptional;
    }

    private void updateMvtStk(Integer idCommande){

        List<LigneCommandeFournisseur> ligneCommandeFournisseurs= ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(idCommande);
        ligneCommandeFournisseurs.forEach(lig ->{
            MvtStkDto mvtStkDto = MvtStkDto.builder()
                    .article(ArticleDto.fromEntity(lig.getArticle()))
                    .dateMvt(Instant.now())
                    .typeMvt(TypeMvtStk.ENTREE)
                    .sourceMvt(SourceMvtStk.COMMANDE_FOURNISSEUR)
                    .quantite(lig.getQuantite())
                    .idEntreprise(lig.getIdEntreprise())
                    .build();
            mvtStkService.entreeStock(mvtStkDto);
        });
    }
}
