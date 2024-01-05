package org.bellotech.SpringRestdemo.payload.album;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    @Schema(description = "Album name", example = "Travel", requiredMode = RequiredMode.REQUIRED)
    private String name;


    @NotBlank
    @Schema(description = "Description of the name", example = "Description", requiredMode = RequiredMode.REQUIRED)
    private String description;
    
}
