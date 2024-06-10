package com.vti.Final.Java.Advance.service.account;

import com.vti.Final.Java.Advance.entity.Account;
import com.vti.Final.Java.Advance.form.account.AccountFilterForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IAccountService extends UserDetailsService {
    public void createAccount(Account account);
    Page<Account> findAllAccounts(Pageable pageable, String search, AccountFilterForm accountFilterForm);
    public Account findAccountByID(int id);
    public Account findAccountByUsername(String username);
    public Account findAccountByName(String name);
    public void updateAccount(Account account);
    public void deleteAccountByID(int id);
    public void deleteAccountByName(String username);
    public boolean isAccountExistsByUsername(String username);
    public boolean isAccountExistsByID(int id);
}
