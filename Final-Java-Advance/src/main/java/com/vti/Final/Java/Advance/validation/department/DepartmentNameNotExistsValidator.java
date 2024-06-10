package com.vti.Final.Java.Advance.validation.department;

import com.vti.Final.Java.Advance.service.department.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DepartmentNameNotExistsValidator implements ConstraintValidator<DepartmentNameNotExists, String> {
    @Autowired
    private IDepartmentService service;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {

        if (StringUtils.isEmpty(name)) {
            return true;
        }

        return !service.isDepartmentExistsByName(name);
    }
}
