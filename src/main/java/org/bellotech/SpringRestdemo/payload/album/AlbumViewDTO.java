package org.bellotech.SpringRestdemo.payload.album;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AlbumViewDTO {

    
private long id;


   
    @Schema(description = "Album name", example = "Travel", requiredMode = RequiredMode.REQUIRED)
    private String name;


   
    @Schema(description = "Description of the name", example = "Description", requiredMode = RequiredMode.REQUIRED)
    private String description;

    private List<PhotoDTO> photos;
    
}
