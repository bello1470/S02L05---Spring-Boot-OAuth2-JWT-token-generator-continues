package org.bellotech.SpringRestdemo.payload.album;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDTO {

    
    @Schema(description = "Album ", example = "Travel", requiredMode = RequiredMode.REQUIRED)
    private String name;


   
    @Schema(description = "Description the name", example = "Description", requiredMode = RequiredMode.REQUIRED)
    private String description;
    
}
