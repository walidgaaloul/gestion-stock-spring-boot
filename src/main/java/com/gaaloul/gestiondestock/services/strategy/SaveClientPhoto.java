package com.gaaloul.gestiondestock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import com.gaaloul.gestiondestock.dto.ClientDto;
import com.gaaloul.gestiondestock.exception.ErrorCodes;
import com.gaaloul.gestiondestock.exception.InvalideOperationException;
import com.gaaloul.gestiondestock.model.Client;
import com.gaaloul.gestiondestock.services.ClientService;
import com.gaaloul.gestiondestock.services.FlickrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("clientStrategy")
@Slf4j
public class SaveClientPhoto implements Strategy<ClientDto>{
    private FlickrService flickrService;
    private ClientService clientService;

    @Autowired
    public SaveClientPhoto(FlickrService flickrService, ClientService clientService) {
        this.flickrService = flickrService;
        this.clientService = clientService;
    }

    @Override
    public ClientDto savePhoto(Integer id,InputStream photo, String titre) throws FlickrException {
        ClientDto client = clientService.findById(id);
        String urlPhoto = flickrService.savaPhoto(photo,titre);
        if (!StringUtils.hasLength(urlPhoto)){

            throw new InvalideOperationException("erreur lors de l'enregistrement de la photo de client ", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        client.setPhoto(urlPhoto);
        return clientService.save(client);
    }
}
