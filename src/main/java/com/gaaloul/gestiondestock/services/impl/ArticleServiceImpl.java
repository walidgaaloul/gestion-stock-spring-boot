package com.gaaloul.gestiondestock.services.impl;

import com.gaaloul.gestiondestock.dto.ArticleDto;
import com.gaaloul.gestiondestock.dto.LigneCommandeClientDto;
import com.gaaloul.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.gaaloul.gestiondestock.dto.LigneVenteDto;
import com.gaaloul.gestiondestock.exception.EntityNotFoundException;
import com.gaaloul.gestiondestock.exception.ErrorCodes;
import com.gaaloul.gestiondestock.exception.InvalideEntityException;
import com.gaaloul.gestiondestock.exception.InvalideOperationException;
import com.gaaloul.gestiondestock.model.Article;
import com.gaaloul.gestiondestock.model.LigneCommandeClient;
import com.gaaloul.gestiondestock.model.LigneCommandeFournisseur;
import com.gaaloul.gestiondestock.model.LigneVente;
import com.gaaloul.gestiondestock.repository.*;
import com.gaaloul.gestiondestock.services.ArticleService;
import com.gaaloul.gestiondestock.validator.ArticleValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    private LigneVenteRepository venteRepository;
    private LigneCommandeClientRepository commandeClientRepository;
    private LigneCommandeFournisseurRepository commandeFournisseurRepository;
    private CategoryRepository categoryRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, LigneVenteRepository venteRepository, LigneCommandeClientRepository commandeClientRepository, LigneCommandeFournisseurRepository commandeFournisseurRepository, CategoryRepository categoryRepository) {

        this.articleRepository=articleRepository;
        this.venteRepository = venteRepository;
        this.commandeClientRepository = commandeClientRepository;
        this.commandeFournisseurRepository = commandeFournisseurRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ArticleDto save(ArticleDto dto) {
        List<String> errors = ArticleValidator.validate(dto );

        if (!errors.isEmpty()){
            log.error("Article is not valid {}",dto);
            throw new InvalideEntityException("L'article n'est aps valide", ErrorCodes.ARTICLE_NOT_VALID,errors);
        }

        return ArticleDto.fromEntity(
                articleRepository
                        .save(ArticleDto.toEntity(dto)));
    }

    @Override
    public ArticleDto findById(Integer id) {

        if (id == null){
            log.error("Article ID is null");
            return null;
        }
        Optional<Article> article = articleRepository.findById(id);

        return Optional.of(ArticleDto.fromEntity(article.get())).orElseThrow(() ->
                new EntityNotFoundException("Aucun article avec ID ="+ id + "n'as été trouver dans DB",ErrorCodes.ARTICLE_NOT_FOUND));
    }

    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {
        if (!StringUtils.hasLength(codeArticle)){
            log.error("Article codeArticle is null");
            return null;
        }
        Optional<Article> article = articleRepository.findArticleByCodeArticle(codeArticle);

        return Optional.of(ArticleDto.fromEntity(article.get())).orElseThrow(() ->
                new EntityNotFoundException("Aucun article avec codeArticle ="+ codeArticle + "n'as été trouver dans DB",ErrorCodes.ARTICLE_NOT_FOUND));
    }

    @Override
    public List<ArticleDto> findAll() {

        return articleRepository.findAll().stream().map(ArticleDto :: fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<LigneVenteDto> findHistoriqueVentes(Integer idArticle) {
        return venteRepository.findAllByArticleId(idArticle).stream()
                .map(LigneVenteDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idArticle) {
        return commandeClientRepository.findAllByArticleId(idArticle).stream()
                .map(LigneCommandeClientDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(Integer idArticle) {
        return commandeFournisseurRepository.findAllByArticleId(idArticle).stream()
                .map(LigneCommandeFournisseurDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<ArticleDto> findAllArticleByIdCategory(Integer idCategory) {
        return articleRepository.findAllByCategoryId(idCategory).stream()
                .map(ArticleDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {

        if (id == null){
            log.error("Article ID is null");
            return ;
        }

        List<LigneCommandeClient> ligneCommandeClients = commandeClientRepository.findAllByArticleId(id);
        if (!ligneCommandeClients.isEmpty()){
            throw new InvalideOperationException("impossible de supprimer un article deja utiliser dans des commande clients",ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }

        List<LigneCommandeFournisseur> ligneCommandeFournisseurs = commandeFournisseurRepository.findAllByArticleId(id);
        if (!ligneCommandeFournisseurs.isEmpty()){
            throw new InvalideOperationException("impossible de supprimer un article deja utiliser dans des commande Fournisseurs",ErrorCodes.ARTICLE_ALREADY_IN_USE);

        }

        List<LigneVente> ligneVentes = venteRepository.findAllByArticleId(id);
        if (!ligneVentes.isEmpty()){
            throw new InvalideOperationException("impossible de supprimer un article deja utiliser dans des ventes",ErrorCodes.ARTICLE_ALREADY_IN_USE);

        }
        articleRepository.deleteById(id);

    }
}
