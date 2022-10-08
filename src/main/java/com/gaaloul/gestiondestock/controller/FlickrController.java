package com.gaaloul.gestiondestock.controller;

import com.flickr4java.flickr.FlickrException;
import com.gaaloul.gestiondestock.controller.api.FlickrApi;
import com.gaaloul.gestiondestock.services.FlickrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

@RestController
public class FlickrController implements FlickrApi {
    private FlickrService flickrService;
@Autowired
    public FlickrController(FlickrService flickrService) {
        this.flickrService = flickrService;
    }

    @Override
    public String savaPhoto(InputStream photo, String title) throws FlickrException {
        return flickrService.savaPhoto(photo,title);
    }
}
