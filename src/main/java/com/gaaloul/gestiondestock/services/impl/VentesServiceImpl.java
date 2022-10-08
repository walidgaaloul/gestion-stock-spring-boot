package com.gaaloul.gestiondestock.services.impl;

import com.gaaloul.gestiondestock.dto.ArticleDto;
import com.gaaloul.gestiondestock.dto.LigneVenteDto;
import com.gaaloul.gestiondestock.dto.MvtStkDto;
import com.gaaloul.gestiondestock.dto.VentesDto;
import com.gaaloul.gestiondestock.exception.EntityNotFoundException;
import com.gaaloul.gestiondestock.exception.ErrorCodes;
import com.gaaloul.gestiondestock.exception.InvalideEntityException;
import com.gaaloul.gestiondestock.exception.InvalideOperationException;
import com.gaaloul.gestiondestock.model.*;
import com.gaaloul.gestiondestock.repository.ArticleRepository;
import com.gaaloul.gestiondestock.repository.LigneVenteRepository;
import com.gaaloul.gestiondestock.repository.VentesRepository;
import com.gaaloul.gestiondestock.services.MvtStkService;
import com.gaaloul.gestiondestock.services.VentesService;
import com.gaaloul.gestiondestock.validator.VentesValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VentesServiceImpl implements VentesService {
    private ArticleRepository articleRepository;
    private VentesRepository ventesRepository;
    private LigneVenteRepository ligneVenteRepository;
    private MvtStkService mvtStkService;
@Autowired
    public VentesServiceImpl(ArticleRepository articleRepository, VentesRepository ventesRepository, LigneVenteRepository ligneVenteRepository, MvtStkService mvtStkService) {
        this.articleRepository = articleRepository;
        this.ventesRepository = ventesRepository;
        this.ligneVenteRepository = ligneVenteRepository;
    this.mvtStkService = mvtStkService;
}

    @Override
    public VentesDto save(VentesDto dto) {
    List<String> errors = VentesValidator.validate(dto);
        if (!errors.isEmpty()){
            log.error("vente n'est pas valide");
            throw new InvalideEntityException("l'objet vente n'est pas valide", ErrorCodes.VENTES_NOT_VALID,errors);
        }

        List<String>articleErrors= new ArrayList<>();

        dto.getLigneVentes().forEach(ligneVenteDto -> {
            Optional<Article> article = articleRepository.findById(ligneVenteDto.getArticle().getId());
            if (article.isEmpty()) {
                articleErrors.add("aucun article avec l'ID " + ligneVenteDto.getArticle().getId() + "n'a ete trouver dans la BD");

            }
        });
           if (!articleErrors.isEmpty()){
               log.error("One or more article were not found in DB{}",errors);
               throw new InvalideEntityException("un ou plusieur article n'ont pas ete trouver dans la BD", ErrorCodes.VENTES_NOT_VALID,errors);
           }
            Ventes savedventes = ventesRepository.save(VentesDto.toEntity(dto));

        dto.getLigneVentes().forEach(ligneVenteDto -> {
            LigneVente ligneVente = LigneVenteDto.toEntity(ligneVenteDto);
            ligneVente.setVente(savedventes);
            ligneVenteRepository.save(ligneVente);
            updateMvtStk(ligneVente);

        });

        return VentesDto.fromEntity(savedventes);
    }

    @Override
    public VentesDto findById(Integer id) {
    if (id==null){
        log.error("vente ID is null");
        return null;
    }
        return ventesRepository.findById(id).map(VentesDto::fromEntity).orElseThrow(()->
               new EntityNotFoundException("Aucun vente avec ID"+id+"n'as été trouver dans DB",ErrorCodes.VENTES_NOT_FOUND));
    }

    @Override
    public VentesDto findByCode(String code) {
    if (!StringUtils.hasLength(code)){
        log.error("vente code is null");
        return null;
    }
        return ventesRepository.findVentesByCode(code).map(VentesDto::fromEntity).orElseThrow(()->
                new EntityNotFoundException("Aucun vente avec code"+code+"n'as été trouver dans DB",ErrorCodes.VENTES_NOT_FOUND));
    }

    @Override
    public List<VentesDto> findAll() {
        return ventesRepository.findAll().stream().map(VentesDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
    if (id==null){
        log.error("vente ID is null");
        return;
    }
        List<LigneVente> ligneVentes = ligneVenteRepository.findAllByCommandeVenteId(id);
        if (!ligneVentes.isEmpty()){
            throw new InvalideOperationException("impossible de supprimer ce ligne Ventes deja utiliser ",ErrorCodes.VENTES_ALREADY_IN_USE);

        }
     ventesRepository.deleteById(id);
    }

    private void updateMvtStk(LigneVente lig){


            MvtStkDto mvtStkDto = MvtStkDto.builder()
                    .article(ArticleDto.fromEntity(lig.getArticle()))
                    .dateMvt(Instant.now())
                    .typeMvt(TypeMvtStk.SORTIE)
                    .sourceMvt(SourceMvtStk.VENTE)
                    .quantite(lig.getQuantite())
                    .idEntreprise(lig.getIdEntreprise())
                    .build();
            mvtStkService.entreeStock(mvtStkDto);

    }
}
