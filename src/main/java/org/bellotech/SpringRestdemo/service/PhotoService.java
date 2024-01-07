package org.bellotech.SpringRestdemo.service;

import java.util.Optional;

import org.bellotech.SpringRestdemo.model.Photo;
import org.bellotech.SpringRestdemo.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {
    

    @Autowired
    private PhotoRepository photoRepository;

    public Photo save(Photo photo) {

  return photoRepository.save(photo);
    }

    public Optional<Photo> findById(long id ){
        return photoRepository.findById(id);
    }
}
