package org.bellotech.SpringRestdemo.controller;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.bellotech.SpringRestdemo.payload.auth.Token;
import org.bellotech.SpringRestdemo.payload.auth.UserLogin;
import org.bellotech.SpringRestdemo.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@Tag(name ="Auth Controller", description = "Controller for account manager" )
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/token")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Token> token(@RequestBody UserLogin userLogin) throws AuthenticationException{
        try{
        Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(userLogin.email(), userLogin.password()));
        return ResponseEntity.ok( new Token(tokenService.generateToken(authentication)));

    }catch (Exception e){
        log.debug("null");
        return new ResponseEntity<>(new Token(null), HttpStatus.BAD_REQUEST);
    }
}
    
}
