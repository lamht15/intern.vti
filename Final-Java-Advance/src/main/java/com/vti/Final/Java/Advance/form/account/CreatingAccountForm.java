package com.vti.Final.Java.Advance.form.account;

import com.vti.Final.Java.Advance.entity.Account;
import com.vti.Final.Java.Advance.validation.account.AccountUsernameNotExists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatingAccountForm {
    @NotBlank(message = "{Account.createAccount.form.username}")
    @Length(max = 50, message = "{Account.createAccount.form.username.Length}")
    @AccountUsernameNotExists
    private String username;
    @NotBlank(message = "{Account.createAccount.form.password}")
    @Length(max = 800, message = "{Account.createAccount.form.password.Length}")
    private String password;
    @NotBlank(message = "{Account.createAccount.form.firstname}")
    @Length(max = 50, message = "{Account.createAccount.form.firstname.Length}")
    private String firstname;
    @NotBlank(message = "{Account.createAccount.form.lastname}")
    @Length(max = 50, message = "{Account.createAccount.form.lastname.Length}")
    private String lastname;
    @NotNull(message = "{Account.createAccount.form.role}")
    private Account.ROLE role;
    @Positive(message = "{Account.createAccount.form.deaparmentID.Min}")
    private int departmentID;
}
