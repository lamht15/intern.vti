package com.vti.Final.Java.Advance.service.department;

import com.vti.Final.Java.Advance.entity.Account;
import com.vti.Final.Java.Advance.entity.Department;
import com.vti.Final.Java.Advance.form.department.CreatingDepartmentForm;
import com.vti.Final.Java.Advance.form.department.DepartmentFilterForm;
import com.vti.Final.Java.Advance.repository.IAccountRepository;
import com.vti.Final.Java.Advance.repository.IDepartmentRepository;
import com.vti.Final.Java.Advance.specification.DepartmentSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DepartmentService implements IDepartmentService{
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private IDepartmentRepository iDepartmentRepository;
    @Autowired
    private IAccountRepository iAccountRepository;
    @Transactional
    @Override
    public void createDepartment(CreatingDepartmentForm creatingDepartmentForm) {
        //Convert
        Department departmentEntity = modelMapper.map(creatingDepartmentForm, Department.class);
        //Tạo department
        Department department = iDepartmentRepository.save(departmentEntity);
        //Ánh xạ sang bảng kia
//        List<Account> accountList = departmentEntity.getAccountList();
//        for(Account account : accountList){
//            account.setDepartment(department);
//        }
//        iAccountRepository.saveAll(accountList);
    }

    @Override
    public Page<Department> findAllDepartments(Pageable pageable, String search, DepartmentFilterForm departmentFilterForm) {
        Specification<Department> where = DepartmentSpecification.buildWhere(search, departmentFilterForm);
        return iDepartmentRepository.findAll(where, pageable);
    }

    @Override
    public Department findDepartmentByID(int id) {
        return iDepartmentRepository.findById(id).get();
    }

    @Override
    public Department findDepartmentByName(String name) {
        return iDepartmentRepository.findByName(name);
    }

    @Override
    public void updateDepartment(Department department) {
        iDepartmentRepository.save(department);
    }

    @Override
    public void deleteDepartmentByID(int id) {
        iDepartmentRepository.deleteById(id);
    }

    @Override
    public void deleteDepartmentByName(String name) {
        iDepartmentRepository.deleteByName(name);
    }

    @Override
    public boolean isDepartmentExistsByName(String name) {
        return iDepartmentRepository.existsByName(name);
    }

    @Override
    public boolean isDepartmentExistsByID(int id) {
        return iDepartmentRepository.existsById(id);
    }

}
