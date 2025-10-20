package uz.tuit.portfolio.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = uz.tuit.portfolio.util.EmailValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmail {

    String message() default "Email must be a valid @gmail.com address";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
