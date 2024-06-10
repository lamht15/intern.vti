package com.vti.Final.Java.Advance.validation.account;

import com.vti.Final.Java.Advance.service.account.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
public class AccountUsernameNotExistsValidator implements ConstraintValidator<AccountUsernameNotExists, String> {
    @Autowired
    IAccountService iAccountService;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if(StringUtils.isEmpty(username)){
            return true;
        }
        return !iAccountService.isAccountExistsByUsername(username);
    }
}
