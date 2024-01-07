package org.bellotech.SpringRestdemo.repository;

import java.util.List;

import org.bellotech.SpringRestdemo.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long>{

    //List<Photo> findByAccount_id(long id);
    
}
