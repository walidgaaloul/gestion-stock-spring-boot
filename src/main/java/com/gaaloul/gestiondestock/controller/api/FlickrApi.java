package com.gaaloul.gestiondestock.controller.api;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.gaaloul.gestiondestock.dto.ClientDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.InputStream;

import static com.gaaloul.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT+"/flickrs")
public interface FlickrApi {

    @PostMapping(value = APP_ROOT + "/flickrs/create" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE )
    @ApiOperation(value = "Enregistrer une photo (ajouter/modifier)" ,notes = "cette methode permet d'enregistrer ou modifier une photo",response = Flickr.class)
    @ApiResponses(value = {
            @ApiResponse( code = 200 , message = "l'objet photo creer / modifier"),
            @ApiResponse( code = 400 , message = "l'objet photo n'est pas valide")
    })
    String savaPhoto(@RequestBody InputStream photo , String title) throws FlickrException;
}
