package com.gaaloul.gestiondestock.controller.api;

import com.gaaloul.gestiondestock.dto.FournisseurDto;
import com.gaaloul.gestiondestock.dto.MvtStkDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.gaaloul.gestiondestock.utils.Constants.*;

@Api(MVTSTK_ENDPOINT)
public interface MvtStkApi {

    @GetMapping(value = MVTSTK_ENDPOINT+ "/stockreel/{idArticle}" , produces = MediaType.APPLICATION_JSON_VALUE)
    BigDecimal stockReelArticle(@PathVariable("idArticle")Integer idArticle);

    @GetMapping(value = MVTSTK_ENDPOINT+ "/filter/article/{idArticle}" , produces = MediaType.APPLICATION_JSON_VALUE)
    List<MvtStkDto> mvtStkArticle(@PathVariable("idArticle")Integer idArticle);

    @PostMapping(value = MVTSTK_ENDPOINT + "/entree" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE )
    MvtStkDto entreeStock(@RequestBody MvtStkDto dto);

    @PostMapping(value = MVTSTK_ENDPOINT + "/sortie" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE )
    MvtStkDto sortieStock(@RequestBody MvtStkDto dto);

    @PostMapping(value = MVTSTK_ENDPOINT + "/correctionpos" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE )
    MvtStkDto correctionStockPos(@RequestBody MvtStkDto dto);

    @PostMapping(value = MVTSTK_ENDPOINT + "/correctionneg" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE )
    MvtStkDto correctionStockNeg(@RequestBody MvtStkDto dto);
}

