package com.vti.Final.Java.Advance.form.department;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vti.Final.Java.Advance.entity.Account;
import com.vti.Final.Java.Advance.entity.Department;
import com.vti.Final.Java.Advance.validation.account.AccountUsernameNotExists;
import com.vti.Final.Java.Advance.validation.department.DepartmentNameNotExists;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class UpdatingDepartmentForm {
    private int id;
    @NotBlank(message = "{Department.createDepartment.form.name.NotBlank}")
    @Length(max = 50, message = "{Department.createDepartment.form.name.Length}")
    @DepartmentNameNotExists(message = "{Department.createDepartment.form.name.NotExists}")
    private String name;
    @PositiveOrZero(message = "{Department.createDepartment.form.totalMember}")
    private int total_member;
    @NotNull(message = "{Department.createDepartment.form.type.NotNull}")
    private Department.TYPE type;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdDate;

//    @NotEmpty(message = "{Department.createDepartment.form.accountList.NotEmpty}")
    private List<@Valid Account> accountList;

    @Data
    @NoArgsConstructor
    public static class Account{
        @NotBlank(message = "{Account.createAccount.form.username}")
        @AccountUsernameNotExists
        @Length(max = 50, message = "{Account.createAccount.form.username.Length}")
        private String username;
        @Length(max = 800, message = "{Account.createAccount.form.password.Length}")
        @NotBlank(message = "{Account.createAccount.form.password}")
        private String password;
        @NotBlank(message = "{Account.createAccount.form.lastname}")
        @Length(max = 50, message = "{Account.createAccount.form.lastname.Length}")
        private String lastname;
        @NotBlank(message = "{Account.createAccount.form.firstname}")
        @Length(max = 50, message = "{Account.createAccount.form.firstname.Length}")
        private String firstname;
        @NotNull(message = "Account.createAccount.form.role")
        private com.vti.Final.Java.Advance.entity.Account.ROLE role;
    }
}
