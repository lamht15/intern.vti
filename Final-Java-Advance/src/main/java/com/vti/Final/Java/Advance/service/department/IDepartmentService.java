package com.vti.Final.Java.Advance.service.department;

import com.vti.Final.Java.Advance.entity.Department;
import com.vti.Final.Java.Advance.form.department.CreatingDepartmentForm;
import com.vti.Final.Java.Advance.form.department.DepartmentFilterForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDepartmentService {
    public void createDepartment(CreatingDepartmentForm creatingDepartmentForm);
    public Page<Department> findAllDepartments(Pageable pageable, String search, DepartmentFilterForm departmentFilterForm);
    public Department findDepartmentByID(int id);
    public Department findDepartmentByName(String name);
    public void updateDepartment(Department department);
    public void deleteDepartmentByID(int id);
    public void deleteDepartmentByName(String name);
    public boolean isDepartmentExistsByName(String name);
    public boolean isDepartmentExistsByID(int id);
}
