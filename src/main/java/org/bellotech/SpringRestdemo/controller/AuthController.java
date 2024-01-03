package org.bellotech.SpringRestdemo.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bellotech.SpringRestdemo.model.Account;
import org.bellotech.SpringRestdemo.payload.auth.AccountDTO;
import org.bellotech.SpringRestdemo.payload.auth.AccountViewDTO;
import org.bellotech.SpringRestdemo.payload.auth.AuthorityDTO;
import org.bellotech.SpringRestdemo.payload.auth.PasswordDTO;
import org.bellotech.SpringRestdemo.payload.auth.ProfileDTO;
import org.bellotech.SpringRestdemo.payload.auth.TokenDTO;
import org.bellotech.SpringRestdemo.payload.auth.UserLoginDTO;
import org.bellotech.SpringRestdemo.service.AccountServices;
import org.bellotech.SpringRestdemo.service.TokenService;
import org.bellotech.SpringRestdemo.utils.constant.AccountError;
import org.bellotech.SpringRestdemo.utils.constant.AccountSuccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@Tag(name ="Auth Controller", description = "Controller for account manager" )
@Slf4j
public class AuthController {

    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private  TokenService tokenService;
    @Autowired
    private AccountServices accountServices;


  
    @PostMapping("/token")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TokenDTO> token(@RequestBody UserLoginDTO userLogin) throws AuthenticationException{
        try{
        Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword()));
        return ResponseEntity.ok( new TokenDTO(tokenService.generateToken(authentication)));

    }catch (Exception e){
        log.debug(AccountError.TOKEN_GENERATION_ERROR.toString() + " :" + e.getMessage());
        return new ResponseEntity<>(new TokenDTO(null), HttpStatus.BAD_REQUEST);
    }
}
@SecurityRequirement(name = "bellotech-myPoject-api")
@PostMapping(value="/users/add", consumes = "application/json", produces = "application/json")
@Operation(summary = "Add a new User")
@ApiResponse(responseCode = "400", description = "please enter valid email and password lenght between 6 to 20 characters")
@ResponseStatus(HttpStatus.OK)
public ResponseEntity<String> addUser (@Valid @RequestBody AccountDTO accountDTO){
try{
    Account account = new Account();
    account.setEmail(accountDTO.getEmail());
    account.setPassword(accountDTO.getPassword());
  
    accountServices.save(account);
    return ResponseEntity.ok(AccountSuccess.ACCOUNT_ADDED.toString());
}catch(Exception e){
log.debug(AccountError.ADD_ACCOUNT_ERROR.toString()+" : " + e.getMessage());
return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
}
}

@SecurityRequirement(name = "bellotech-myPoject-api")
@GetMapping(value="/users", produces = "application/json")
@Operation(summary = "List of Users")
@ApiResponse(responseCode = "200", description = "List of users")
@ApiResponse(responseCode = "401", description = "Token missing")
@ApiResponse(responseCode = "403", description = "Token Error")
public List<AccountViewDTO> Users(){

    List<AccountViewDTO> accounts = new ArrayList<>();
for (Account account : accountServices.findAll()) {
    accounts.add(new AccountViewDTO(account.getId(),account.getEmail(),account.getAuthorities()));
    
}

    return accounts;
} 

@SecurityRequirement(name = "bellotech-myPoject-api")
@GetMapping(value="/profile", produces = "application/json")
@Operation(summary = "Profile")
@ApiResponse(responseCode = "200", description = "Profile")
@ApiResponse(responseCode = "401", description = "Token missing")
@ApiResponse(responseCode = "403", description = "Token Error")
public ProfileDTO profile(Authentication authentication){
    String email = authentication.getName();
    Optional <Account> optionalAccount = accountServices.findByEmail(email);
    if (optionalAccount.isPresent()) {

        Account account = optionalAccount.get();
  
   
  ProfileDTO profileDTO =  new ProfileDTO(account.getId(),account.getEmail(),account.getAuthorities());
  return profileDTO;
        
    }
    return null;
    
}
@SecurityRequirement(name = "bellotech-myPoject-api")
@PutMapping(value="/profile/password-update", produces = "application/json")
@Operation(summary = "password update")
@ApiResponse(responseCode = "200", description = "Profile")
@ApiResponse(responseCode = "401", description = "Token missing")
@ApiResponse(responseCode = "403", description = "Token Error")
public AccountViewDTO passwordUpdate(@Valid @RequestBody PasswordDTO passwordDTO, Authentication authentication){

    String email = authentication.getName();
    Optional <Account> optionalAccount = accountServices.findByEmail(email);
    if (optionalAccount.isPresent()) {
        Account account= optionalAccount.get();
        account.setPassword(passwordDTO.getPassword());
        accountServices.save(account);
        AccountViewDTO accountViewDTO = new AccountViewDTO(account.getId(),account.getEmail(),account.getAuthorities());
        return accountViewDTO;
        
    }
    return null;

}
@SecurityRequirement(name = "bellotech-myPoject-api")
@PutMapping(value="/profile/auth-upddate{id}", produces = "application/json")
@Operation(summary = "Auth update")
@ApiResponse(responseCode = "200", description = "Authority_update")
@ApiResponse(responseCode = "401", description = "Token missing")
@ApiResponse(responseCode = "403", description = "Token Error")
public AccountViewDTO passwordUpdate(@Valid @RequestBody AuthorityDTO authorityDTO, @PathVariable long id){

   
    Optional <Account> optionalAccount = accountServices.findById(id);
    if (optionalAccount.isPresent()) {
        Account account= optionalAccount.get();
        account.setAuthorities(authorityDTO.getAuthorities());
        accountServices.save(account);
        AccountViewDTO accountViewDTO = new AccountViewDTO(account.getId(),account.getEmail(),account.getAuthorities());
        return accountViewDTO;
        
    }
    return null;

}
}