package com.vti.Final.Java.Advance.validation.account;

import com.vti.Final.Java.Advance.service.account.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AccountIdExistsValidator implements ConstraintValidator<AccountIdExists, Integer> {
    @Autowired
    IAccountService iAccountService;

    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext constraintValidatorContext) {
        if(StringUtils.isEmpty(id)){
            return true;
        }
        return iAccountService.isAccountExistsByID(id);
    }
}
