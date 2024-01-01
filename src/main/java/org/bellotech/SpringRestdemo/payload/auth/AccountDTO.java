package org.bellotech.SpringRestdemo.payload.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AccountDTO {
    @Email
    @Schema(description = "email address", example = "admin@bellotech.org", requiredMode = RequiredMode.REQUIRED)
    private String email;
    @Size(min = 6, max = 18)
    @Schema(description = "password", example = "password", requiredMode = RequiredMode.REQUIRED, maxLength = 18, minLength = 6)
    private String password;
}
