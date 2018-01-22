package com.wikift.support.validate.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserEmailExistsValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserEmailExists {

    String message() default "该邮箱已经存在, 请使用其他邮箱";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
