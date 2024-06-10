package com.vti.Final.Java.Advance.repository;

import com.vti.Final.Java.Advance.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IAccountRepository extends JpaRepository<Account, Integer>, JpaSpecificationExecutor<Account> {
    Account findByUsername(String username);
    Account findByFirstname(String first_name);
    Account findByLastname(String last_name);
    void deleteByUsername(String name);
    boolean existsByUsername(String username);
}
