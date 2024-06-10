package com.vti.Final.Java.Advance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vti.Final.Java.Advance.entity.Account;
import com.vti.Final.Java.Advance.entity.Department;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class AccountDTO {
    private int id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private Account.ROLE role;
    private DepartmentForm department;
    @Data
    @NoArgsConstructor
    static class DepartmentForm{
        private int id;
        private String name;
        private int total_member;
        private Department.TYPE type;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Date createdDate;
    }
}
