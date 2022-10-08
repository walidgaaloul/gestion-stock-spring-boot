package com.gaaloul.gestiondestock.dto;

import com.gaaloul.gestiondestock.model.Category;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CategoryDto {

    private Integer id;

    private String codeCategory;

    private String designation;

    private Integer idEntreprise;


   //@JsonIgnore

    private List<ArticleDto> articles;

    public static CategoryDto fromEntity(Category category){
        if(category == null){
            return null;
        }
        //maping de category vers categoryDto
        return CategoryDto.builder()
                .id(category.getId())
                .codeCategory(category.getCodeCategory())
                .designation(category.getDesignation())
                .idEntreprise(category.getIdEntreprise())
                .build();
    }
    public static Category toEntity(CategoryDto categoryDto){
        if(categoryDto == null) {
            return null;
        }
         Category category = new Category();
        category.setId(categoryDto.getId());
        category.setCodeCategory(categoryDto.getCodeCategory());
        category.setDesignation(categoryDto.getDesignation());
        category.setIdEntreprise(categoryDto.getIdEntreprise());
        return category;

    }

}
