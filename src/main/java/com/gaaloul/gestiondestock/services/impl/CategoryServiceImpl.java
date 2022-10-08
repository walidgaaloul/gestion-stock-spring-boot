package com.gaaloul.gestiondestock.services.impl;

import com.gaaloul.gestiondestock.dto.CategoryDto;
import com.gaaloul.gestiondestock.exception.EntityNotFoundException;
import com.gaaloul.gestiondestock.exception.ErrorCodes;
import com.gaaloul.gestiondestock.exception.InvalideEntityException;
import com.gaaloul.gestiondestock.exception.InvalideOperationException;
import com.gaaloul.gestiondestock.model.Article;
import com.gaaloul.gestiondestock.repository.ArticleRepository;
import com.gaaloul.gestiondestock.repository.CategoryRepository;
import com.gaaloul.gestiondestock.services.CategoryService;
import com.gaaloul.gestiondestock.validator.CategoryValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    private ArticleRepository articleRepository;
    public CategoryServiceImpl(CategoryRepository categoryRepository, ArticleRepository articleRepository){
        this.categoryRepository = categoryRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public CategoryDto save(CategoryDto dto) {
        List<String> errors= CategoryValidator.validate(dto);
        if (!errors.isEmpty()){
            log.error("Category is not valid {}",dto);
            throw new InvalideEntityException("La Category n'est aps valide", ErrorCodes.CATEGORY_NOT_VALID,errors);
        }
        return CategoryDto.fromEntity(
                categoryRepository.save(CategoryDto.toEntity(dto)));
    }

    @Override
    public CategoryDto findById(Integer id) {
        if (id == null){
            log.error("category ID is null");
        return null;}

        return categoryRepository.findById(id).map(CategoryDto::fromEntity).orElseThrow(() ->
                new EntityNotFoundException("Aucun Category avec ID ="+ id + "n'as été trouver dans DB",ErrorCodes.CATEGORY_NOT_FOUND));


    }

    @Override
    public CategoryDto findByCodeCategory(String codeCategory) {
        if (!StringUtils.hasLength(codeCategory)){
            log.error("Category codeCategory is null");
            return null;
        }
       return categoryRepository.findCategoryByCodeCategory(codeCategory).map(CategoryDto::fromEntity).orElseThrow(() ->
               new EntityNotFoundException("Aucun Category avec code ="+ codeCategory + "n'as été trouver dans DB",ErrorCodes.CATEGORY_NOT_FOUND));
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(CategoryDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null){
            log.error("Article ID is null");
            return ;
        }
        List<Article>articles = articleRepository.findAllByCategoryId(id);
        if (!articles.isEmpty()){
            throw new InvalideOperationException("impossible de supprimer cette category deja utiliser ",ErrorCodes.CATEGORY_ALREADY_IN_USE);
        }
        categoryRepository.deleteById(id);

    }
}
