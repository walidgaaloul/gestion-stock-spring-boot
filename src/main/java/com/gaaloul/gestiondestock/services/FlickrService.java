package com.gaaloul.gestiondestock.services;

import com.flickr4java.flickr.FlickrException;

import java.io.InputStream;

public interface FlickrService {
    String savaPhoto(InputStream photo , String title) throws FlickrException;
}
