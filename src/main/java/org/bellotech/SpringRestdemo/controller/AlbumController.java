package org.bellotech.SpringRestdemo.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.bellotech.SpringRestdemo.model.Account;
import org.bellotech.SpringRestdemo.model.Album;
import org.bellotech.SpringRestdemo.payload.album.AlbumDTO;
import org.bellotech.SpringRestdemo.payload.album.AlbumViewDTO;
import org.bellotech.SpringRestdemo.service.AccountServices;
import org.bellotech.SpringRestdemo.service.AlbumService;
import org.bellotech.SpringRestdemo.utils.constant.AlbumError;
import org.bellotech.SpringRestdemo.utils.constant.appUtils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/albums")
@Tag(name ="Album Controller", description = "Controller for album and photo management" )
@Slf4j
public class AlbumController {
    @Autowired
    private AccountServices accountServices;

    @Autowired
    private AlbumService albumService;


@SecurityRequirement(name = "bellotech-myPoject-api")
@PostMapping(value="/add", consumes = "application/json", produces = "application/json")
@Operation(summary = "Add an Album")
@ApiResponse(responseCode = "400", description = "please add valid name and description")
@ApiResponse(responseCode = "201", description = "Album added")
@ResponseStatus(HttpStatus.CREATED)
public ResponseEntity<AlbumViewDTO> addAlbum (@Valid @RequestBody AlbumDTO albumDTO, Authentication authentication){

    try{
        Album album = new Album();
        album.setName(albumDTO.getName()); 
        album.setDescription(albumDTO.getDescription());

        String email = authentication.getName();
        Optional<Account> optionalAccount = accountServices.findByEmail(email);
        Account account = optionalAccount.get();
        album.setAccount(account);

        album = albumService.save(album);

        AlbumViewDTO albumViewDTO = new AlbumViewDTO(album.getId(), album.getName(), album.getDescription());

        return ResponseEntity.ok(albumViewDTO);


    } catch(Exception e){

        log.debug(AlbumError.ADD_ALBUM_ERROR.toString() + ":" + e.getMessage() );

    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
}

@SecurityRequirement(name = "bellotech-myPoject-api")
@GetMapping(value="/", consumes = "application/json", produces = "application/json")
@Operation(summary = "list all Album")
@ApiResponse(responseCode = "200",description = "List of albums")
@ApiResponse(responseCode = "401", description = "Token missing")
@ApiResponse(responseCode = "403", description = "Token error")

public List<AlbumViewDTO>  listAlbum (Authentication authentication){

    String email = authentication.getName();
    Optional <Account> optionalAccount = accountServices.findByEmail(email);
        Account account = optionalAccount.get();
        List<AlbumViewDTO> albums = new ArrayList<>();
        for (Album album : albumService.findByAccount_id(account.getId())){
        albums.add(new AlbumViewDTO(album.getId(),album.getName(),album.getDescription()));
        }

    return albums;

    }
    @ApiResponse(responseCode = "400", description = "please check the payload or token")
    @PostMapping(value = "/{album_id}/photos", consumes = {"multipart/form-data"})
    @SecurityRequirement(name = "bellotech-myPoject-api")
    @Operation(summary = "Upload photos")

public ResponseEntity<String> photos (@RequestPart (required = true ) MultipartFile [] files , @PathVariable long album_id, Authentication authentication){

    // now is to provide func. that only profile that is long in to can only upload the photos 
    // according to the path giving
    String email = authentication.getName();
    Optional<Account> optionalAOptional = accountServices.findByEmail(email);
    Account account = optionalAOptional.get();
    Optional<Album> optionalAlbum = albumService.findById(album_id);
    Album album;
    if (optionalAlbum.isPresent()) {
        album = optionalAlbum.get();
        // if the 2 id is not matched means not the owner of the album
        // the account id and album id 
        if (account.getId() != album.getAccount().getId()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }else{
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    List<String> fileNamesWithSuccess = new ArrayList<>();
    List<String> fileNamesWithError = new ArrayList<>();
    Arrays.asList(files).stream().forEach(file  -> {
    String contentType = file.getContentType();
    if (contentType.equals("image/png") || 
     contentType.equals("image/jpg") || 
     contentType.equals("image/jpeg")){
        fileNamesWithSuccess.add(file.getOriginalFilename());
        int lenght = 10;
        boolean usLetters = true;
        boolean useNumbers = true ;
        try {
            String fileName = file.getOriginalFilename();
            String generatedString = RandomStringUtils.random(lenght,usLetters,useNumbers);
            String final_photo_name = generatedString + fileName;
            String absolute_fileLocation = AppUtils.get_photo_upload_path(final_photo_name, album_id);
            Path path = Paths.get(absolute_fileLocation);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            // TODO: handle exception
        }
        
    }
    
  
    });
    return null;
}
}
