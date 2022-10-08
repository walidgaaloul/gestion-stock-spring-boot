package com.gaaloul.gestiondestock.services.impl;

import com.gaaloul.gestiondestock.dto.CategoryDto;
import com.gaaloul.gestiondestock.exception.EntityNotFoundException;
import com.gaaloul.gestiondestock.exception.ErrorCodes;
import com.gaaloul.gestiondestock.exception.InvalideEntityException;
import com.gaaloul.gestiondestock.services.CategoryService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryService service;

    @Test
    public void shouldSaveCategoryWithSuccess(){
        CategoryDto expectedCategory = CategoryDto.builder()
                .codeCategory("Cat test")
                .designation("designation test")
                .idEntreprise(1)
                .build();

        CategoryDto SavedCategory = service.save(expectedCategory);

        assertNotNull(SavedCategory);
        assertNotNull(SavedCategory.getId());
        assertEquals(expectedCategory.getCodeCategory() , SavedCategory.getCodeCategory());
        assertEquals(expectedCategory.getDesignation() , SavedCategory.getDesignation());
        assertEquals(expectedCategory.getIdEntreprise() , SavedCategory.getIdEntreprise());


    }

    @Test
    public void shouldUpdadateCategoryWithSuccess(){
        CategoryDto expectedCategory = CategoryDto.builder()
                .codeCategory("Cat test")
                .designation("designation test")
                .idEntreprise(1)
                .build();

        CategoryDto SavedCategory = service.save(expectedCategory);

        CategoryDto categoryToUpdate = SavedCategory;
        categoryToUpdate.setCodeCategory("Cat update");

        SavedCategory = service.save(categoryToUpdate);

        assertNotNull(categoryToUpdate);
        assertNotNull(categoryToUpdate.getId());
        assertEquals(categoryToUpdate.getCodeCategory() , SavedCategory.getCodeCategory());
        assertEquals(categoryToUpdate.getDesignation() , SavedCategory.getDesignation());
        assertEquals(categoryToUpdate.getIdEntreprise() , SavedCategory.getIdEntreprise());


    }


    @Test
    public void shouldThrowInvalidEntityException(){
        CategoryDto expectedCategory = CategoryDto.builder().build();

        InvalideEntityException expectedException = assertThrows(InvalideEntityException.class, () -> service.save(expectedCategory));

      assertEquals(ErrorCodes.CATEGORY_NOT_VALID , expectedException.getErrorCodes());
      assertEquals(1,expectedException.getErrors().size());
      assertEquals("veuillez renseigner le code de la categorie ",expectedException.getErrors().get(0));

    }

    @Test
    public void shouldThrowEntityNotFoundException(){

        EntityNotFoundException expectedException = assertThrows(EntityNotFoundException.class, () -> service.findById(0));

        assertEquals(ErrorCodes.CATEGORY_NOT_FOUND , expectedException.getErrorCode());
        assertEquals("Aucun Category avec ID =0n'as été trouver dans DB",expectedException.getMessage());

    }


    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowEntityNotFoundException2(){

        service.findById(0);

    }


}