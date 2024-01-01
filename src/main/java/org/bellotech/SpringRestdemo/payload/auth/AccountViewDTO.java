package org.bellotech.SpringRestdemo.payload.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AccountViewDTO {
    private Long id;

    private String email;
    
    private String role;
    
}
