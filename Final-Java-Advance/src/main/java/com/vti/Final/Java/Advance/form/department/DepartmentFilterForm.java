package com.vti.Final.Java.Advance.form.department;

import com.vti.Final.Java.Advance.entity.Department;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
public class DepartmentFilterForm {
    private Integer minID;
    private Integer maxID;
    private Department.TYPE type;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date minCreatedDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date maxCreatedDate;
    private Integer year;
}
