package com.vti.Final.Java.Advance.specification;

import com.vti.Final.Java.Advance.entity.Department;
import com.vti.Final.Java.Advance.form.department.DepartmentFilterForm;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

public class DepartmentSpecification {
    public static Specification<Department> buildWhere(String search, DepartmentFilterForm departmentFilterForm){
        Specification<Department> where = null;
        if(!StringUtils.isEmpty(search)){
            search = search.trim();
            DepartmentCustomSpecification name = new DepartmentCustomSpecification("name", search);
//            DepartmentCustomSpecification accountUsername = new DepartmentCustomSpecification("accountUsername", search);
            where = Specification.where(name);
//                    .or(accountUsername);
        }
        if(departmentFilterForm != null & departmentFilterForm.getType() != null){
            DepartmentCustomSpecification type = new DepartmentCustomSpecification("type", departmentFilterForm.getType());
            if(where == null){
                where = Specification.where(type);
            }
            else {
                where = where.and(type);
            }
        }
        if(departmentFilterForm != null & departmentFilterForm.getMinCreatedDate() != null){
            DepartmentCustomSpecification minCreatedDate = new DepartmentCustomSpecification("minCreatedDate", departmentFilterForm.getMinCreatedDate());
            if(where == null){
                where = Specification.where(minCreatedDate);
            }
            else {
                where = where.and(minCreatedDate);
            }
        }
        if(departmentFilterForm != null & departmentFilterForm.getMaxCreatedDate() != null){
            DepartmentCustomSpecification maxCreatedDate = new DepartmentCustomSpecification("maxCreatedDate", departmentFilterForm.getMaxCreatedDate());
            if(where == null){
                where = Specification.where(maxCreatedDate);
            }
            else {
                where = where.and(maxCreatedDate);
            }
        }
        if(departmentFilterForm != null & departmentFilterForm.getCreatedDate() != null){
            DepartmentCustomSpecification createdDate = new DepartmentCustomSpecification("createdDate", departmentFilterForm.getCreatedDate());
            if(where == null){
                where = Specification.where(createdDate);
            }
            else {
                where = where.and(createdDate);
            }
        }
        if(departmentFilterForm != null & departmentFilterForm.getMinID() != null){
            DepartmentCustomSpecification minID = new DepartmentCustomSpecification("minID", departmentFilterForm.getMinID());
            if(where == null){
                where = Specification.where(minID);
            }
            else {
                where = where.and(minID);
            }
        }
        if(departmentFilterForm != null & departmentFilterForm.getMaxID() != null){
            DepartmentCustomSpecification maxID = new DepartmentCustomSpecification("maxID", departmentFilterForm.getMaxID());
            if(where == null){
                where = Specification.where(maxID);
            }
            else {
                where = where.and(maxID);
            }
        }
        return where;
    }
}
@RequiredArgsConstructor
class DepartmentCustomSpecification implements Specification<Department>{
    @NonNull
    private String field;
    @NonNull
    private Object value;
    public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder){
        if(field.equalsIgnoreCase("name")){
            return criteriaBuilder.like(root.get("name"), "%" + value.toString() + "%");
        }
        if(field.equalsIgnoreCase("type")){
            return criteriaBuilder.equal(root.get("type"), value);
        }
        if(field.equalsIgnoreCase("createdDate")){
            return criteriaBuilder.equal(root.get("createdDate").as(java.sql.Date.class), (Date) value);
        }
        if (field.equalsIgnoreCase("minCreatedDate")){
            return criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate").as(java.sql.Date.class), (Date) value);
        }
        if (field.equalsIgnoreCase("maxCreatedDate")){
            return criteriaBuilder.lessThanOrEqualTo(root.get("createdDate").as(java.sql.Date.class), (Date) value);
        }
//        if(field.equalsIgnoreCase("accountUsername")){
//            return criteriaBuilder.like(root.get("accountList").get("username"), "%" + value.toString() + "%");
//        }
        if(field.equalsIgnoreCase("minID")){
            return criteriaBuilder.greaterThanOrEqualTo(root.get("id"), value.toString());
        }
        if(field.equalsIgnoreCase("maxID")){
            return criteriaBuilder.lessThanOrEqualTo(root.get("id"), value.toString());
        }
        return null;
    }
}
