package uz.tuit.portfolio.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = uz.tuit.portfolio.util.UsernameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUsername {
    String message() default "Username must start with a letter, be at least 6 characters, and contain only letters and digits";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
