package com.vti.Final.Java.Advance.controller;

import com.vti.Final.Java.Advance.dto.AccountDTO;
import com.vti.Final.Java.Advance.entity.Account;
import com.vti.Final.Java.Advance.form.account.AccountFilterForm;
import com.vti.Final.Java.Advance.form.account.CreatingAccountForm;
import com.vti.Final.Java.Advance.form.account.UpdatingAccountForm;
import com.vti.Final.Java.Advance.service.account.IAccountService;
import com.vti.Final.Java.Advance.validation.account.AccountIdExists;
import com.vti.Final.Java.Advance.validation.account.AccountUsernameNotExists;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@Validated
@CrossOrigin("*")
public class AccountController {
    @Autowired
    private IAccountService iAccountService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;
    @PostMapping("/create")
    public void createAccount(@RequestBody @Valid CreatingAccountForm creatingAccountForm){
        TypeMap<CreatingAccountForm, Account> typeMap = modelMapper.getTypeMap(CreatingAccountForm.class, Account.class);
        if(typeMap == null){
            modelMapper.addMappings(new PropertyMap<CreatingAccountForm, Account>() {
                @Override
                protected void configure(){
                    skip(destination.getId());
                }
            });
        }
        Account account = modelMapper.map(creatingAccountForm, Account.class);
        account.setPassword(passwordEncoder.encode(creatingAccountForm.getPassword()));
        iAccountService.createAccount(account);
    }
    @GetMapping("/filter")
    public Page<AccountDTO> findAllAccounts(Pageable pageable, @RequestParam(value = "search", required = false) String search, AccountFilterForm accountFilterForm){
        Page<Account> accountPage = iAccountService.findAllAccounts(pageable, search, accountFilterForm);
        //account to form
        List<AccountDTO> accountDTOList = modelMapper.map(accountPage.getContent(), new TypeToken<List<AccountDTO>>(){}.getType());
        Page<AccountDTO> accountFormPage = new PageImpl<>(accountDTOList, pageable, accountPage.getTotalElements());
        return accountFormPage;
    }
    @GetMapping("/{id}")
    public AccountDTO findAccountByID(@PathVariable("id") @AccountIdExists int id){
        Account account = iAccountService.findAccountByID(id);
        AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
        return accountDTO;
    }
    @GetMapping("/username")
    public AccountDTO findAccountByUsername(@RequestParam("username") String username){
        Account account = iAccountService.findAccountByUsername(username);
        AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
        return accountDTO;
    }
    @GetMapping("/name")
    public AccountDTO findAccountByName(@RequestParam("name") String name){
        Account account = iAccountService.findAccountByName(name);
        AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
        return accountDTO;
    }
    @PutMapping("/update")
    public void updateAccount(@RequestParam("id") @AccountIdExists @Valid int id, @RequestBody @Valid UpdatingAccountForm updatingAccountForm){
        TypeMap<UpdatingAccountForm, Account> typeMap = modelMapper.getTypeMap(UpdatingAccountForm.class, Account.class);
        if(typeMap == null){
            modelMapper.addMappings(new PropertyMap<UpdatingAccountForm, Account>() {
                @Override
                protected void configure(){
                    skip(destination.getId());
                }
            });
        }
        Account account = modelMapper.map(updatingAccountForm, Account.class);
        account.setId(id);
        account.setPassword(passwordEncoder.encode(updatingAccountForm.getPassword()));
        iAccountService.updateAccount(account);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteAccountByID(@PathVariable("id") @AccountIdExists int id){
        iAccountService.deleteAccountByID(id);
    }
    @DeleteMapping("/delete/username")
    public void deleteAccountByID(@RequestParam("username")  String username){
        iAccountService.deleteAccountByName(username);
    }
//@AccountUsernameNotExists
}
