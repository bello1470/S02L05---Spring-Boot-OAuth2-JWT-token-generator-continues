package org.bellotech.SpringRestdemo.payload.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import lombok.Getter;

import lombok.Setter;

@Setter
@Getter
public class PasswordDTO {

     
    @Size(min = 3, max = 18)
    @Schema(description = "333", example = "password", requiredMode = RequiredMode.REQUIRED, maxLength = 18, minLength = 3)
    private String password;
}
