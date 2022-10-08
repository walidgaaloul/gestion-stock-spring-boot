package com.gaaloul.gestiondestock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import com.gaaloul.gestiondestock.dto.EntrepriseDto;
import com.gaaloul.gestiondestock.exception.ErrorCodes;
import com.gaaloul.gestiondestock.exception.InvalideOperationException;
import com.gaaloul.gestiondestock.model.Entreprise;
import com.gaaloul.gestiondestock.services.EntrepriseService;
import com.gaaloul.gestiondestock.services.FlickrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;
@Service("entrepriseStrategy")
@Slf4j
public class SaveEntreprisePhoto implements Strategy<EntrepriseDto>{

    private FlickrService flickrService;
    private EntrepriseService entrepriseService;

    @Autowired
    public SaveEntreprisePhoto(FlickrService flickrService, EntrepriseService entrepriseService) {
        this.flickrService = flickrService;
        this.entrepriseService = entrepriseService;
    }

    @Override
    public EntrepriseDto savePhoto(Integer id,InputStream photo, String titre) throws FlickrException {
        EntrepriseDto entreprise= entrepriseService.findById(id);
        String urlPhoto= flickrService.savaPhoto(photo,titre);
        if (!StringUtils.hasLength(urlPhoto)){

            throw new InvalideOperationException("erreur lors de l'enregistrement de la photo de entreprise ", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        entreprise.setPhoto(urlPhoto);
        return entrepriseService.save(entreprise);
    }
}
