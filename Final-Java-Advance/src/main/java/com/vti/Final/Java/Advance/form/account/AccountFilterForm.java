package com.vti.Final.Java.Advance.form.account;


import com.vti.Final.Java.Advance.entity.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountFilterForm {
    private Integer minID;
    private Integer maxID;
    private Account.ROLE role;
    private String departmentName;
    private String firstname;
    private String lastname;
}
