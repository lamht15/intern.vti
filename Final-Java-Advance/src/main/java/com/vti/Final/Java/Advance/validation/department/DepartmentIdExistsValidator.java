package com.vti.Final.Java.Advance.validation.department;

import com.vti.Final.Java.Advance.service.department.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DepartmentIdExistsValidator implements ConstraintValidator<DepartmentIdExists, Integer> {
    @Autowired
    IDepartmentService iDepartmentService;
    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext constraintValidatorContext){
        if(StringUtils.isEmpty(id)){
            return true;
        }
        return iDepartmentService.isDepartmentExistsByID(id);
    }
}
