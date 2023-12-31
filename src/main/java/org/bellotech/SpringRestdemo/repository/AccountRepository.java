package org.bellotech.SpringRestdemo.repository;

import java.util.Optional;

import org.bellotech.SpringRestdemo.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);
    
}
