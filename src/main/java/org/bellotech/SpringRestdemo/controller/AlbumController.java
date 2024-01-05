package org.bellotech.SpringRestdemo.controller;

import org.bellotech.SpringRestdemo.model.Album;
import org.bellotech.SpringRestdemo.payload.admin.AlbumDTO;
import org.bellotech.SpringRestdemo.payload.admin.AlbumViewDTO;
import org.bellotech.SpringRestdemo.service.AccountServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/album")
@Tag(name ="Auth Controller", description = "Controller for album and photo management" )
@Slf4j
public class AlbumController {
    @Autowired
    private AccountServices accountServices;

    private Album


@SecurityRequirement(name = "bellotech-myPoject-api")
@PostMapping(value="/albums/add", consumes = "application/json", produces = "application/json")
@Operation(summary = "Add an Album")
@ApiResponse(responseCode = "400", description = "please add valid name and description")
@ApiResponse(responseCode = "201", description = "Album added")
@ResponseStatus(HttpStatus.OK)
public ResponseEntity<AlbumViewDTO> addAlbum (@Valid @RequestBody AlbumDTO albumDTO, Authentication authentication){

    try{
        Album album = new Album();
        album.setName(albumDTO.getName()); 
        album.setDescription(albumDTO.getDescription());

        String email = authentication.getName();


    } catch(Exception e){

    }
}

}