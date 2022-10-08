package com.gaaloul.gestiondestock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import com.gaaloul.gestiondestock.dto.UtilisateurDto;
import com.gaaloul.gestiondestock.exception.ErrorCodes;
import com.gaaloul.gestiondestock.exception.InvalideOperationException;
import com.gaaloul.gestiondestock.model.Utilisateur;
import com.gaaloul.gestiondestock.services.FlickrService;
import com.gaaloul.gestiondestock.services.UtilisateurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;
@Service("utilisateurStrategy")
@Slf4j
public class SaveUtilisateurPhoto implements Strategy<UtilisateurDto>{
    private FlickrService flickrService;
    private UtilisateurService utilisateurService;

    @Autowired
    public SaveUtilisateurPhoto(FlickrService flickrService, UtilisateurService utilisateurService) {
        this.flickrService = flickrService;
        this.utilisateurService = utilisateurService;
    }

    @Override
    public UtilisateurDto savePhoto(Integer id,InputStream photo, String titre) throws FlickrException {
        UtilisateurDto utilisateur = utilisateurService.findById(id);
        String urlPhoto = flickrService.savaPhoto(photo,titre);

        if (!StringUtils.hasLength(urlPhoto)){

            throw new InvalideOperationException("erreur lors de l'enregistrement de la photo de utilisateur ", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }

        utilisateur.setPhoto(urlPhoto);

        return utilisateurService.save(utilisateur);
    }
}
