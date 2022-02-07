package com.mybank2.mybank.dao;


import com.mybank2.mybank.entities.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account , Long> {
    Account findByAccountNumberEquals(String accountNumber);
}
