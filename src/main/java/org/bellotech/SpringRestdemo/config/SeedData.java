package org.bellotech.SpringRestdemo.config;

import org.bellotech.SpringRestdemo.model.Account;
import org.bellotech.SpringRestdemo.service.AccountServices;
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
        account1.setEmail("user1");
        account1.setPassword("222");
        account1.setRole("ROLE_USER");
        accountServices.save(account1);

         
        Account account2 = new Account();
        account2.setEmail("admin2");
        account2.setPassword("333");
        account2.setRole("ROLE_ADMIN");
        accountServices.save(account2);
    }
    
}
