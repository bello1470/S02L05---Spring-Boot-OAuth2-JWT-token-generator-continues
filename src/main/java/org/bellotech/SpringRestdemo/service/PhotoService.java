package org.bellotech.SpringRestdemo.service;

import java.util.List;
import java.util.Optional;

import org.bellotech.SpringRestdemo.model.Album;
import org.bellotech.SpringRestdemo.model.Photo;
import org.bellotech.SpringRestdemo.repository.AlbumRepository;
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
       public List<Photo> findByAccount_id(long id){

        return photoRepository.findByAccount_id(id);
    }

    public Optional<Photo> findById(long id ){
        return photoRepository.findById(id);
    }
}
