package com.vti.Final.Java.Advance.repository;

import com.vti.Final.Java.Advance.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IDepartmentRepository extends JpaRepository<Department, Integer>, JpaSpecificationExecutor<Department> {
    Department findByName(String name);
    void deleteByName(String name);
    boolean existsByName(String name);
}
