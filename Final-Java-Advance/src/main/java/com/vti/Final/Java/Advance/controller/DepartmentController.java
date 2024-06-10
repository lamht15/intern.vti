package com.vti.Final.Java.Advance.controller;

import com.vti.Final.Java.Advance.dto.DepartmentDTO;
import com.vti.Final.Java.Advance.entity.Department;
import com.vti.Final.Java.Advance.form.department.CreatingDepartmentForm;
import com.vti.Final.Java.Advance.form.department.DepartmentFilterForm;
import com.vti.Final.Java.Advance.form.department.UpdatingDepartmentForm;
import com.vti.Final.Java.Advance.service.department.IDepartmentService;
import com.vti.Final.Java.Advance.validation.department.DepartmentIdExists;
import com.vti.Final.Java.Advance.validation.department.DepartmentNameNotExists;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/v1/departments")
@Validated
@CrossOrigin("*")
public class DepartmentController {
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private IDepartmentService iDepartmentService;
    @Autowired
    private ModelMapper modelMapper;
    @PostMapping("/create")
    public void createDepartment(@RequestBody @Valid CreatingDepartmentForm creatingDepartmentForm){
        iDepartmentService.createDepartment(creatingDepartmentForm);
    }
    @GetMapping("/filter")
    public Page<DepartmentDTO> findAllDepartments(Pageable pageable, @RequestParam(value = "search", required = false) String search, DepartmentFilterForm departmentFilterForm){
        Page<Department> departmentPage = iDepartmentService.findAllDepartments(pageable, search, departmentFilterForm);
        //Convert sang DTO
        List<DepartmentDTO> departmentDTOList = modelMapper.map(departmentPage.getContent(), new TypeToken<List<DepartmentDTO>>(){}.getType());
        Page<DepartmentDTO> departmentFormPage = new PageImpl<>(departmentDTOList, pageable, departmentPage.getTotalElements());
        return departmentFormPage;
    }
    @GetMapping("/{id}")
    public DepartmentDTO findDepartmentByID(@PathVariable("id") @DepartmentIdExists @Valid int id){
        Department department = iDepartmentService.findDepartmentByID(id);
        DepartmentDTO departmentDTO = modelMapper.map(department, DepartmentDTO.class);
        return departmentDTO;
    }
    @GetMapping("/name")
    public DepartmentDTO findDepartmentByName(@RequestParam("name") String name){
        Department department = iDepartmentService.findDepartmentByName(name);
        DepartmentDTO departmentDTO = modelMapper.map(department, DepartmentDTO.class);
        return departmentDTO;
    }
    //@DepartmentIdExists
    @PutMapping("/update")
    public void updateDeparment(@RequestParam("id")  int id, @RequestBody UpdatingDepartmentForm updatingDepartmentForm){
        updatingDepartmentForm.setId(id);
        Department department = modelMapper.map(updatingDepartmentForm, Department.class);
        iDepartmentService.updateDepartment(department);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteDepartmentByID(@PathVariable("id") @DepartmentIdExists int id){
        iDepartmentService.deleteDepartmentByID(id);
    }
    //@DepartmentNameNotExists
    @DeleteMapping("/delete")
    public void deleteDepartmentByName(@RequestParam("name")  String name){
        iDepartmentService.deleteDepartmentByName(name);
    }
}
