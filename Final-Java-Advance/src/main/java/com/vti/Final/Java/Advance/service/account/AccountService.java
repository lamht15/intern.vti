package com.vti.Final.Java.Advance.service.account;

import com.vti.Final.Java.Advance.entity.Account;
import com.vti.Final.Java.Advance.form.account.AccountFilterForm;
import com.vti.Final.Java.Advance.repository.IAccountRepository;
import com.vti.Final.Java.Advance.specification.AccountSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountService implements IAccountService{
    @Autowired
    private IAccountRepository iAccountRepository;
    @Transactional
    @Override
    public void createAccount(Account account) {
        iAccountRepository.save(account);
    }

    @Override
    public Page<Account> findAllAccounts(Pageable pageable, String search, AccountFilterForm accountFilterForm) {
        Specification<Account> where = AccountSpecification.buildWhere(search, accountFilterForm);
        return iAccountRepository.findAll(where, pageable);
    }

    @Override
    public Account findAccountByID(int id) {
        return iAccountRepository.findById(id).get();
    }

    @Override
    public Account findAccountByUsername(String username) {
        return iAccountRepository.findByUsername(username);
    }

    @Override
    public Account findAccountByName(String name) {
        List<Account> accountList = iAccountRepository.findAll();
        String name_nospace = name.replaceAll("\\s", "");
        for(Account account : accountList){
            String fullname = account.getFirstname() + " " + account.getLastname();
            String fullname_nospace = fullname.replaceAll("\\s", "");
            if(name_nospace.equalsIgnoreCase(fullname_nospace)){
                return account;
            }
        }
        return null;
    }

    @Override
    public void updateAccount(Account account) {
        iAccountRepository.save(account);
    }

    @Override
    public void deleteAccountByID(int id) {
        iAccountRepository.deleteById(id);
    }

    @Override
    public void deleteAccountByName(String username) {
        iAccountRepository.deleteByUsername(username);
    }

    @Override
    public boolean isAccountExistsByUsername(String username) {
        return iAccountRepository.existsByUsername(username);
    }

    @Override
    public boolean isAccountExistsByID(int id) {
        return iAccountRepository.existsById(id);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Account account = iAccountRepository.findByUsername(username);
        if(account == null){
            throw new UsernameNotFoundException(username);
        }
        return new User(
                account.getUsername(),
                account.getPassword(),
                AuthorityUtils.createAuthorityList(account.getRole().toString())
        );
    }
}
