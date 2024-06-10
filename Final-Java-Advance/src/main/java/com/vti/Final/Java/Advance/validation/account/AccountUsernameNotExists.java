package com.vti.Final.Java.Advance.validation.account;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })//Áp dụng cho các đối tượng
@Retention(RUNTIME)//Giữ trong quá trình chạy
@Documented//Ghi lại trong tài liệu JAVA?
@Constraint(validatedBy = { AccountUsernameNotExistsValidator.class })//Nếu bộ valid trả về false -> message()
public @interface AccountUsernameNotExists {
    String message() default "Account Username already exists!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List{//xử lý khi có nhiều @AccountUsernameNotExists được xài
        AccountUsernameNotExists[] value();
    }
}
