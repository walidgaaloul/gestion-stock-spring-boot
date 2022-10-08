package com.gaaloul.gestiondestock.services.impl;

import com.gaaloul.gestiondestock.dto.MvtStkDto;
import com.gaaloul.gestiondestock.exception.EntityNotFoundException;
import com.gaaloul.gestiondestock.exception.ErrorCodes;
import com.gaaloul.gestiondestock.exception.InvalideEntityException;
import com.gaaloul.gestiondestock.model.MvtStk;
import com.gaaloul.gestiondestock.model.TypeMvtStk;
import com.gaaloul.gestiondestock.repository.MvtStkRepository;
import com.gaaloul.gestiondestock.services.ArticleService;
import com.gaaloul.gestiondestock.services.MvtStkService;
import com.gaaloul.gestiondestock.validator.MvtStkValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MvtStkServiceImpl implements MvtStkService {
private MvtStkRepository mvtStkRepository;
private ArticleService articleService;

@Autowired
    public MvtStkServiceImpl(MvtStkRepository mvtStkRepository, ArticleService articleService) {
        this.mvtStkRepository = mvtStkRepository;
        this.articleService = articleService;
    }

    @Override
    public BigDecimal stockReelArticle(Integer idArticle) {

        if (idArticle == null){
            log.warn("ID article is null");
            return BigDecimal.valueOf(-1);
        }
        articleService.findById(idArticle);
        return mvtStkRepository.stockReelArticle(idArticle);
    }

    @Override
    public List<MvtStkDto> mvtStkArticle(Integer idArticle) {
        return mvtStkRepository.findAllByArticleId(idArticle).stream()
                .map(MvtStkDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public MvtStkDto entreeStock(MvtStkDto dto) {

     return entreePositive(TypeMvtStk.ENTREE,dto);
    }

    @Override
    public MvtStkDto sortieStock(MvtStkDto dto) {
        return sortieNegative(TypeMvtStk.SORTIE,dto);
    }

    @Override
    public MvtStkDto correctionStockPos(MvtStkDto dto) {
      return entreePositive(TypeMvtStk.CORRECTION_POS,dto);
    }

    @Override
    public MvtStkDto correctionStockNeg(MvtStkDto dto) {
      return sortieNegative(TypeMvtStk.CORRECTION_NEG,dto);
    }

    private MvtStkDto entreePositive(TypeMvtStk typeMvtStk , MvtStkDto dto){

        List<String> errors = MvtStkValidator.validate(dto);
        if (!errors.isEmpty()){
            log.error("MvtStk is not valid {}",dto);
            throw new InvalideEntityException("La MvtStk n'est aps valide", ErrorCodes.MVT_STK_NOT_VALID,errors);
        }
        dto.setQuantite(BigDecimal.valueOf(Math.abs(dto.getQuantite().doubleValue())
                )
        );
        dto.setTypeMvt(typeMvtStk);
        return MvtStkDto.fromEntity(
                mvtStkRepository.save(MvtStkDto.toEntity(dto))
        );
    }

    private MvtStkDto sortieNegative(TypeMvtStk typeMvtStk , MvtStkDto dto){
        List<String> errors = MvtStkValidator.validate(dto);
        if (!errors.isEmpty()){
            log.error("MvtStk is not valid {}",dto);
            throw new InvalideEntityException("La MvtStk n'est aps valide", ErrorCodes.MVT_STK_NOT_VALID,errors);
        }
        dto.setQuantite(BigDecimal.valueOf(Math.abs(dto.getQuantite().doubleValue())*-1
        ));
        dto.setTypeMvt(typeMvtStk);
        return MvtStkDto.fromEntity(
                mvtStkRepository.save(MvtStkDto.toEntity(dto))
        );
    }
}
