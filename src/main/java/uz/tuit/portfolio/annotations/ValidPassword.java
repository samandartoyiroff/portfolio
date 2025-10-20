package uz.tuit.portfolio.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = uz.tuit.portfolio.util.PasswordValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Password must be at least 6 characters";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
