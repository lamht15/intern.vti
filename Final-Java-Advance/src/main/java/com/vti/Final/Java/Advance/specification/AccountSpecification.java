package com.vti.Final.Java.Advance.specification;

import com.vti.Final.Java.Advance.entity.Account;
import com.vti.Final.Java.Advance.form.account.AccountFilterForm;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class AccountSpecification {
    public static Specification<Account> buildWhere(String search, AccountFilterForm accountFilterForm){
        Specification<Account> where = null;
        if(!StringUtils.isEmpty(search)){
            search = search.trim(); //Xóa space đâu cuối
            AccountCustomSpecification username = new AccountCustomSpecification("username", search); //field để filter
            AccountCustomSpecification firstname = new AccountCustomSpecification("firstname", search);
            AccountCustomSpecification lastname = new AccountCustomSpecification("lastname", search);
            where = Specification.where(username).or(firstname).or(lastname);
        }
        if(accountFilterForm != null & accountFilterForm.getDepartmentName() != null){
            AccountCustomSpecification departmentName = new AccountCustomSpecification("departmentName", accountFilterForm.getDepartmentName());
            if(where == null){
                where = Specification.where(departmentName);
            }
            else {
                where = where.and(departmentName);
            }
        }
        if(accountFilterForm != null & accountFilterForm.getRole() != null){
            AccountCustomSpecification role = new AccountCustomSpecification("role", accountFilterForm.getRole());
            if(where == null){
                where = Specification.where(role);
            }
            else {
                where = where.and(role);
            }
        }
        if(accountFilterForm != null & accountFilterForm.getMinID() != null){
            AccountCustomSpecification minID = new AccountCustomSpecification("minID", accountFilterForm.getMinID());
            if(where == null){
                where = Specification.where(minID);
            }
            else {
                where = where.and(minID);
            }
        }
        if(accountFilterForm != null & accountFilterForm.getMaxID() != null){
            AccountCustomSpecification maxID = new AccountCustomSpecification("maxID", accountFilterForm.getMaxID());
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
class AccountCustomSpecification implements Specification<Account> {
    @NonNull
    private String field; //2 tham số tương ứng - có thể k cần = @NonNull
    @NonNull
    private Object value; // Bất kể kiểu dữ liệu j
    public Predicate toPredicate(
            Root<Account> root,
            CriteriaQuery<?> criteriaQuery,
            CriteriaBuilder criteriaBuilder
    ){
        if(field.equalsIgnoreCase("username")){
            return criteriaBuilder.like(root.get("username"), "%" + value.toString() + "%");
        }
        if(field.equalsIgnoreCase("firstname")){
            return criteriaBuilder.like(root.get("firstname"), "%" + value.toString() + "%");
        }
        if(field.equalsIgnoreCase("lastname")){
            return criteriaBuilder.like(root.get("lastname"), "%" + value.toString() + "%");
        }
        if(field.equalsIgnoreCase("role")){
            return criteriaBuilder.equal(root.get("role"), value);
        }
        if(field.equalsIgnoreCase("departmentName")){
            return criteriaBuilder.like(root.get("department").get("name"), "%" + value.toString() + "%");
        }
        if(field.equalsIgnoreCase("minID")){
            return criteriaBuilder.greaterThanOrEqualTo(root.get("id"), value.toString());
        }
        if (field.equalsIgnoreCase("maxID")){
            return criteriaBuilder.lessThanOrEqualTo(root.get("id"), value.toString());
        }
        return null;
    }
}
