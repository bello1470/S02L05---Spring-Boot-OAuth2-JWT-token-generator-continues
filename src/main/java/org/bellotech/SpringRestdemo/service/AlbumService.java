package org.bellotech.SpringRestdemo.service;

import org.bellotech.SpringRestdemo.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumService {
    

    @Autowired
    private AlbumRepository albumRepository;
}
