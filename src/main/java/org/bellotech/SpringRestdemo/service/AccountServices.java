package org.bellotech.SpringRestdemo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bellotech.SpringRestdemo.model.Account;
import org.bellotech.SpringRestdemo.model.Album;
import org.bellotech.SpringRestdemo.repository.AccountRepository;
import org.bellotech.SpringRestdemo.utils.constant.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServices implements UserDetailsService{

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Account save(Account account){
        if (account.getAuthorities() == null) {
            account.setAuthorities(Authority.USER.toString());
            
        }
        // Encoding the user's password before saving it to the database.
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);

       
    }

    public void deleteByID(long id){
        accountRepository.deleteById(id);
    }

    public Optional<Account> findByEmail(String email){

        return accountRepository.findByEmail(email);
    }



    public List<Account> findAll(){

        return accountRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       Optional<Account>  optionalAccount = accountRepository.findByEmail(email);
       if (!optionalAccount.isPresent()) {

        throw new UsernameNotFoundException("Account not found");
       }
       Account account = optionalAccount.get();
       List<GrantedAuthority> grantedAuthority = new ArrayList<>();
       grantedAuthority.add(new SimpleGrantedAuthority(account.getAuthorities()));
       return new User(account.getEmail(), account.getPassword(), grantedAuthority);

    }

    public Optional<Account> findById(long id) {
        return accountRepository.findById(id);
    }
 
}