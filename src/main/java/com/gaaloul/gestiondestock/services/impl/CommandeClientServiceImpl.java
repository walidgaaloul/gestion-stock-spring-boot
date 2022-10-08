package com.gaaloul.gestiondestock.services.impl;

import com.gaaloul.gestiondestock.dto.*;
import com.gaaloul.gestiondestock.exception.EntityNotFoundException;
import com.gaaloul.gestiondestock.exception.ErrorCodes;
import com.gaaloul.gestiondestock.exception.InvalideEntityException;
import com.gaaloul.gestiondestock.exception.InvalideOperationException;
import com.gaaloul.gestiondestock.model.*;
import com.gaaloul.gestiondestock.repository.ArticleRepository;
import com.gaaloul.gestiondestock.repository.ClientRepository;
import com.gaaloul.gestiondestock.repository.CommandeClientRepository;
import com.gaaloul.gestiondestock.repository.LigneCommandeClientRepository;
import com.gaaloul.gestiondestock.services.CommandeClientService;
import com.gaaloul.gestiondestock.services.MvtStkService;
import com.gaaloul.gestiondestock.validator.ArticleValidator;
import com.gaaloul.gestiondestock.validator.CommandeClientValidator;
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
public class CommandeClientServiceImpl implements CommandeClientService {
    private CommandeClientRepository commandeClientRepository;
    private ClientRepository clientRepository;
    private ArticleRepository articleRepository;

    private LigneCommandeClientRepository ligneCommandeClientRepository;

    private MvtStkService mvtStkService;

    @Autowired
    public CommandeClientServiceImpl(CommandeClientRepository commandeClientRepository, ClientRepository clientRepository, ArticleRepository articleRepository , LigneCommandeClientRepository ligneCommandeClientRepository, MvtStkService mvtStkService) {
        this.commandeClientRepository = commandeClientRepository;
        this.clientRepository = clientRepository;
        this.articleRepository = articleRepository;
        this.ligneCommandeClientRepository=ligneCommandeClientRepository;

        this.mvtStkService = mvtStkService;
    }

    @Override
    public CommandeClientDto save(CommandeClientDto dto) {
        List<String> errors = CommandeClientValidator.validate(dto);
        if (!errors.isEmpty()){
            log.error("commande client n'est pas valide");
            throw new InvalideEntityException("commande client n'est pas valide", ErrorCodes.COMMANDE_CLIENT_NOT_VALID,errors);
        }

        if (dto.getId() != null && dto.isCommandeLivree()){
            throw new InvalideOperationException("impossible de modifier la commande lorsqu'elle est livree",ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);

        }

        Optional<Client> client = clientRepository.findById(dto.getClient().getId());
        if (!client.isPresent()){
            log.warn("Clieny with ID{} was not found in the DB",dto.getClient().getId());
            throw new EntityNotFoundException("Aucun client avec ID"+dto.getClient().getId()+"n'as été trouver",ErrorCodes.CLIENT_NOT_FOUND);
        }

        List<String> articleErrors=new ArrayList<>();
        if (dto.getLigneCommandeClients() != null ){
            dto.getLigneCommandeClients().forEach(LigCmdClt ->
            {
                if (LigCmdClt.getArticle() != null){
                    Optional<Article> article = articleRepository.findById(LigCmdClt.getArticle().getId());
                    if (article.isEmpty()){
                        articleErrors.add("l'article avec ID " +LigCmdClt.getArticle().getId()+"n'existe pas");

                    }

                }else {
                    articleErrors.add("impossible d'enregistrer une commande avec un article null");

                }
            });
        }
        if (!articleErrors.isEmpty()){
            log.warn("");
            throw new InvalideEntityException("Article n'existe pas dans la BD",ErrorCodes.COMMANDE_CLIENT_NOT_VALID,articleErrors);
        }
        CommandeClient saveCmdClt = commandeClientRepository.save(CommandeClientDto.toEntity(dto));
        if (dto.getLigneCommandeClients() != null ){
            dto.getLigneCommandeClients().forEach(LigCmdClt ->{
                LigneCommandeClient ligneCommandeClient = LigneCommandeClientDto.toEntity(LigCmdClt);
                ligneCommandeClient.setCommandeClient(saveCmdClt);
                ligneCommandeClientRepository.save(ligneCommandeClient);

            });
        }



        return CommandeClientDto.fromEntity(saveCmdClt);
    }

    @Override
    public CommandeClientDto findById(Integer id) {
        if (id == null){
            log.error("Commande client ID is null");
            return null;
        }
        return commandeClientRepository.findById(id).map(CommandeClientDto::fromEntity).orElseThrow(() ->
                new EntityNotFoundException("Aucun Commande client avec ID"+id+"n'as été trouver dans DB",ErrorCodes.COMMANDE_CLIENT_NOT_FOUND));
    }

    @Override
    public CommandeClientDto findByCode(String code) {
        if (!StringUtils.hasLength(code)){
            log.error("CommandeClient code is null");
            return null;
        }
       return commandeClientRepository.findCommandeClientByCode(code).map(CommandeClientDto::fromEntity).orElseThrow(() ->
                new EntityNotFoundException("Aucun Commande client avec ID"+code+"n'as été trouver dans DB",ErrorCodes.COMMANDE_CLIENT_NOT_FOUND));


    }

    @Override
    public List<CommandeClientDto> findAll() {
        return commandeClientRepository.findAll().stream().map(CommandeClientDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null){
            log.error("Commande client ID is null");
            return;
        }
        List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository.findAllByCommandeClientId(id);
        if (!ligneCommandeClients.isEmpty()){
            throw new InvalideOperationException("impossible de supprimer ce Commande Client deja utiliser ",ErrorCodes.COMMANDE_CLIENT_ALREADY_IN_USE);

        }
        commandeClientRepository.deleteById(id);

    }

    @Override
    public List<LigneCommandeClientDto> findAllLignesCommandesClientByCommandeClientId(Integer idCommande) {
        return ligneCommandeClientRepository.findAllByCommandeClientId(idCommande).stream()
                .map(LigneCommandeClientDto::fromEntity).collect(Collectors.toList());
    }



    @Override
    public CommandeClientDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
       checkIdCommande(idCommande);

        if (!StringUtils.hasLength(String.valueOf(etatCommande))){
            log.error("etat de la commande is null");
            throw new InvalideOperationException("impossible de modifier etat de la commande avec etat de la commande null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);

        }
        CommandeClientDto commandeClient = checkEtatCommande(idCommande);
        commandeClient.setEtatCommande(etatCommande);
        CommandeClient savedCmdClt = commandeClientRepository.save(CommandeClientDto.toEntity(commandeClient));
        if (commandeClient.isCommandeLivree()){
            updateMvtStk(idCommande);}

        return CommandeClientDto.fromEntity(savedCmdClt);
    }

    @Override
    public CommandeClientDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {

        checkIdCommande(idCommande);

        checkIdLigneCommande(idLigneCommande);

        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0){
            log.error("ID de la ligne  de la commande is null");
            throw new InvalideOperationException("impossible de modifier quantiter  avec une quantiter null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);

        }

        CommandeClientDto commandeClient = checkEtatCommande(idCommande);
        Optional<LigneCommandeClient> ligneCommandeClientOptional = findLigneCommandeClient(idLigneCommande);
        LigneCommandeClient ligneCommandeClient = ligneCommandeClientOptional.get();
        ligneCommandeClient.setQuantite(quantite);
        ligneCommandeClientRepository.save(ligneCommandeClient);
        return commandeClient;
    }



    @Override
    public CommandeClientDto updateClient(Integer idCommande, Integer idClient) {

       checkIdCommande(idCommande);

        checkIdClient(idClient);

        CommandeClientDto commandeClient = checkEtatCommande(idCommande);

        Optional<Client> clientOptional = clientRepository.findById(idClient);
        if (clientOptional.isEmpty()){
            throw new EntityNotFoundException("Aucun client avec ID"+idClient+"n'as été trouver dans DB",ErrorCodes.CLIENT_NOT_FOUND);
        }

        commandeClient.setClient(ClientDto.fromEntity(clientOptional.get()));
        return CommandeClientDto.fromEntity(commandeClientRepository.save(CommandeClientDto.toEntity(commandeClient)));
    }




    @Override
    public CommandeClientDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {

       checkIdCommande(idCommande);

       checkIdLigneCommande(idLigneCommande);

        checkIdArticle(idArticle, "nouvel");

        CommandeClientDto commandeClient = checkEtatCommande(idCommande);

        Optional<LigneCommandeClient> ligneCommandeClient = findLigneCommandeClient(idLigneCommande);

        Optional<Article> articleOptional =articleRepository.findById(idArticle);

        if (articleOptional.isEmpty()){
            throw new EntityNotFoundException("Aucun article avec ID"+idArticle+"n'as été trouver dans DB",ErrorCodes.ARTICLE_NOT_FOUND);

        }

        List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(articleOptional.get()));
        if (!errors.isEmpty()){
            throw new InvalideEntityException("article invalide",ErrorCodes.ARTICLE_NOT_VALID,errors);
        }

        LigneCommandeClient ligneCommandeClientToSaved = ligneCommandeClient.get();
        ligneCommandeClientToSaved.setArticle(articleOptional.get());
        ligneCommandeClientRepository.save(ligneCommandeClientToSaved);
        return commandeClient;
    }


    @Override
    public CommandeClientDto deleteArticle(Integer idCommande, Integer idLigneCommande) {

        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        //just check LigneCommandeClient and inform the client in case it is absent
        CommandeClientDto commandeClient = checkEtatCommande(idCommande);
        findLigneCommandeClient(idLigneCommande);
        ligneCommandeClientRepository.deleteById(idLigneCommande);


        return commandeClient;
    }

    private void checkIdCommande(Integer idCommande){

        if (idCommande == null){
            log.error("Commande client ID is null");
            throw new InvalideOperationException("impossible de modifier etat de la commande avec in ID null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);

        }
    }

    private void checkIdLigneCommande(Integer idLigneCommande ){
        if (idLigneCommande == null){
            log.error("ID de la ligne  de la commande is null");
            throw new InvalideOperationException("impossible de modifier Article  avec ID de la ligne  de la commande null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);

        }

    }

    private void checkIdClient(Integer idClient ){
        if (idClient == null){
            log.error("ID de la client  de la commande is null");
            throw new InvalideOperationException("impossible de modifier client  avec ID client null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);

        }
    }

    private void checkIdArticle(Integer idArticle , String msg ){
        if (idArticle == null){
            log.error("ID de "+msg+" Article is null");
            throw new InvalideOperationException("impossible de modifier Article  avec "+msg+" ID Article null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);

        }

    }

    private CommandeClientDto checkEtatCommande(Integer idCommande) {
        CommandeClientDto commandeClient = findById(idCommande);
        if (commandeClient.isCommandeLivree()){
            throw new InvalideOperationException("impossible de modifier la commande lorsqu'elle est livree",ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);

        }
        return commandeClient;
    }

    private Optional<LigneCommandeClient> findLigneCommandeClient(Integer idLigneCommande) {
        Optional<LigneCommandeClient> ligneCommandeClientOptional= ligneCommandeClientRepository.findById(idLigneCommande);

        if (ligneCommandeClientOptional.isEmpty()){
            throw new EntityNotFoundException("Aucun Ligne Commande client avec ID"+idLigneCommande+"n'as été trouver dans DB",ErrorCodes.COMMANDE_CLIENT_NOT_FOUND);
        }
        return ligneCommandeClientOptional;
    }

    private void updateMvtStk(Integer idCommande){

        List<LigneCommandeClient> ligneCommandeClients= ligneCommandeClientRepository.findAllByCommandeClientId(idCommande);
        ligneCommandeClients.forEach(lig ->{
            MvtStkDto mvtStkDto = MvtStkDto.builder()
                    .article(ArticleDto.fromEntity(lig.getArticle()))
                    .dateMvt(Instant.now())
                    .typeMvt(TypeMvtStk.SORTIE)
                    .sourceMvt(SourceMvtStk.COMMANDE_CLIENT)
                    .quantite(lig.getQuantite())
                    .idEntreprise(lig.getIdEntreprise())
                    .build();
            mvtStkService.sortieStock(mvtStkDto);
        });
    }
}
