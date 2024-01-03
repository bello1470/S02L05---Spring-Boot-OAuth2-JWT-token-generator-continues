package org.bellotech.SpringRestdemo.config;

import org.bellotech.SpringRestdemo.model.Account;
import org.bellotech.SpringRestdemo.service.AccountServices;
import org.bellotech.SpringRestdemo.utils.constant.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SeedData implements CommandLineRunner{

    @Autowired
    private AccountServices accountServices;


    @Override
    public void run(String... args) throws Exception {
        
        Account account1 = new Account();
        account1.setEmail("user1@gmail.com");
        account1.setPassword("222");
        account1.setAuthorities(Authority.USER.toString());
        accountServices.save(account1);

         
        Account account2 = new Account();
        account2.setEmail("admin1@gmail.com");
        account2.setPassword("333");
        account2.setAuthorities(Authority.ADMIN.toString() + " " + Authority.USER.toString());
        accountServices.save(account2);
    }
    
}
