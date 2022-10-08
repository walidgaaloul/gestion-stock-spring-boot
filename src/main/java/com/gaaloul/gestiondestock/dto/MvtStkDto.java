package com.gaaloul.gestiondestock.dto;

import com.gaaloul.gestiondestock.model.MvtStk;
import com.gaaloul.gestiondestock.model.SourceMvtStk;
import com.gaaloul.gestiondestock.model.TypeMvtStk;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Data
public class MvtStkDto {
    private Integer id;

    private Instant dateMvt;

    private BigDecimal quantite;


    private ArticleDto article;


    private TypeMvtStk typeMvt;

    private Integer idEntreprise;

    private SourceMvtStk sourceMvt;

    public static MvtStkDto fromEntity (MvtStk mvtStk){
        if(mvtStk == null){
            return null;
        }
        return MvtStkDto.builder()
                .id(mvtStk.getId())
                .dateMvt(mvtStk.getDateMvt())
                .quantite(mvtStk.getQuantite())
                .article(ArticleDto.fromEntity(mvtStk.getArticle()))
                .idEntreprise(mvtStk.getIdEntreprise())
                .sourceMvt(mvtStk.getSourceMvt())
                .build();
    }

    public static MvtStk toEntity(MvtStkDto mvtStkDto){
         if (mvtStkDto == null){
             return null;
         }
         MvtStk mvtStk = new MvtStk();
         mvtStk.setId(mvtStkDto.getId());
         mvtStk.setDateMvt(mvtStkDto.getDateMvt());
         mvtStk.setQuantite(mvtStkDto.getQuantite());
         mvtStk.setArticle(ArticleDto.toEntity(mvtStkDto.getArticle()));
         mvtStk.setIdEntreprise(mvtStkDto.getIdEntreprise());
         mvtStk.setSourceMvt((mvtStkDto.getSourceMvt()));
         return mvtStk;
    }
}
