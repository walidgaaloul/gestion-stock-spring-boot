package com.gaaloul.gestiondestock.controller;

import com.gaaloul.gestiondestock.controller.api.ArticleApi;
import com.gaaloul.gestiondestock.dto.ArticleDto;
import com.gaaloul.gestiondestock.dto.LigneCommandeClientDto;
import com.gaaloul.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.gaaloul.gestiondestock.dto.LigneVenteDto;
import com.gaaloul.gestiondestock.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController implements ArticleApi {


    private ArticleService articleService;


//Constructor injection
    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService=articleService;
    }

    @Override
    public ArticleDto save(ArticleDto dto) {

        return articleService.save(dto);
    }

    @Override
    public ArticleDto findById(Integer id) {
        return articleService.findById(id);
    }

    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {
        return articleService.findByCodeArticle(codeArticle);
    }

    @Override
    public List<ArticleDto> findAll() {
        return articleService.findAll();
    }

    @Override
    public List<LigneVenteDto> findHistoriqueVentes(Integer idArticle) {
        return articleService.findHistoriqueVentes(idArticle);
    }

    @Override
    public List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idArticle) {
        return articleService.findHistoriqueCommandeClient(idArticle);
    }

    @Override
    public List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(Integer idArticle) {
        return articleService.findHistoriqueCommandeFournisseur(idArticle);
    }

    @Override
    public List<ArticleDto> findAllArticleByIdCategory(Integer idCategory) {
        return articleService.findAllArticleByIdCategory(idCategory);
    }

    @Override
    public void delete(Integer id) {

        articleService.delete(id);
    }
}
