package com.wikift.support.validate.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserNameExistsValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserNameExists {

    String message() default "该用户名已经存在, 请使用其他用户名注册";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
