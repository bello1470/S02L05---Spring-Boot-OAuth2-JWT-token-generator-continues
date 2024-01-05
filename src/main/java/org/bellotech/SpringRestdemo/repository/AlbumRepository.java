package org.bellotech.SpringRestdemo.repository;

import org.bellotech.SpringRestdemo.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long>{
    
}
