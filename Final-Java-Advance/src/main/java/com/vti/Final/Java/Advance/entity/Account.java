package com.vti.Final.Java.Advance.entity;

//import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "`Account`")
@Data
@NoArgsConstructor
public class    Account implements Serializable {
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(name = "username", length = 50, nullable = false, unique = true)
    private String username;

    @Column(name = "password", length = 800, nullable = false)
    private String password;

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstname;


    @Column(name = "last_name", length = 50, nullable = false)
    private String lastname;

    @Column(name = "role", nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'EMPLOYEE'")
    @Enumerated(EnumType.STRING)
    private ROLE role;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    public enum ROLE{
        ADMIN, EMPLOYEE, MANAGER
    }
}
